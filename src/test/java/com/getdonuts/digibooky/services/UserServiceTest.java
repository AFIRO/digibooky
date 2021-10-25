package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.CreateUserDto;
import com.getdonuts.digibooky.api.dto.UserDto;
import com.getdonuts.digibooky.domain.User;
import com.getdonuts.digibooky.exceptions.AuthorisationException;
import com.getdonuts.digibooky.repository.UserRepository;
import com.getdonuts.digibooky.services.mapper.UserMapper;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private CreateUserDto dto;
    private User admin;

    @BeforeEach
    void setup() {
        userRepository = new UserRepository();
        userMapper = new UserMapper();
        userService = new UserService(userRepository, userMapper);
        // The hard coded librarian in the repo needs to be commented or removed
        admin = new ArrayList<>(userRepository.getUsers()).get(0);
    }


    @Nested
    @DisplayName("Member Tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TestSaveMember {

        @Test
        void saveMember() {
            //given
            dto = new CreateUserDto("9999", "Jean", "Paul", "jean@paul.fr", "Rue", "69", "Bxl", "1000");
            //when
            UserDto jeanpaul = userService.saveMember(dto);
            //then
            assertEquals("Jean", jeanpaul.getFirstName());
            assertEquals("Paul", jeanpaul.getLastname());
            assertEquals("jean@paul.fr", jeanpaul.getEmail());
            assertEquals("Rue", jeanpaul.getStreet());
            assertEquals("69", jeanpaul.getHouseNumber());
            assertEquals("Bxl", jeanpaul.getCity());
            assertEquals("1000", jeanpaul.getPostcode());
            assertNotNull(jeanpaul.getId());
        }

        @Test
        void whenNoInss_shouldThrowException() {
            // given
            dto = new CreateUserDto(null, "Jean", "Paul", "jean@paul.fr", "Rue", "69", "Bxl", "1000");

            //When
            //UserDto jeanPaul = userService.saveMember(dto);

            //Then
            Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveMember(dto));
        }


        @Test
        void whenNoEmail_shouldThrowException() {
            //given
            dto = new CreateUserDto("9999", "Jean", "Paul", null, "Rue", "69", "Bxl", "1000");
            //then
            assertThrows(IllegalArgumentException.class, () -> userService.saveMember(dto));
        }

        @Test
        void whenNoLastName_shouldThrowException() {
            //given
            dto = new CreateUserDto("9999", "Jean", null, "jean@paul.fr", "Rue", "69", "Bxl", "1000");
            //then
            assertThrows(IllegalArgumentException.class, () -> userService.saveMember(dto));
        }

        @Test
        void whenNoCity_shouldThrowException() {
            //given
            dto = new CreateUserDto("9999", "Jean", "Paul", "jean@paul.fr", "Rue", "69", null, "1000");
            //then
            assertThrows(IllegalArgumentException.class, () -> userService.saveMember(dto));
        }



        @Test
        public void ifEmailAddressExist_throwException(){
            CreateUserDto dto_1 = new CreateUserDto("9998", "Jean", "Paul", "jean@paul1.fr", "Rue", "69", "Bxl", "1000");
            CreateUserDto dto_2 = new CreateUserDto("9997", "Jean", "Paul", "jean@paul1.fr", "Rue", "69", "Bxl", "1000");

            // then
            userService.saveMember(dto_1);

            // when

            assertThrows(IllegalArgumentException.class,() -> userService.saveMember(dto_2));

        }

        @Test
        public void ifInnsExist_throwException(){
            CreateUserDto dto_1 = new CreateUserDto("9998", "Jean", "Paul", "jean@paul1.fr", "Rue", "69", "Bxl", "1000");
            CreateUserDto dto_2 = new CreateUserDto("9998", "Jean", "Paul", "jean@paul2.fr", "Rue", "69", "Bxl", "1000");

            // then
            userService.saveLibrarian(admin.getId(), dto_1);

            // when

            assertThrows(IllegalArgumentException.class,() -> userService.saveLibrarian(admin.getId(), dto_2));

        }
    }

    @Nested
    @DisplayName("Librarian Tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TestSaveLibrarian {

        //private User admin;

        @BeforeAll
        void setup() {
            // The hard coded librarian in the repo needs to be commented or removed
            //admin = new ArrayList<>(userRepository.getUsers()).get(0);
        }

        @Test
        public void whenSavingLibrarian_returnLibrarian(){
            //given
            dto = new CreateUserDto("9997", "Jean", "Paul", "jean@paul2.fr", "Rue", "69", "Bxl", "1000");
            //when
            UserDto jeanpaul = userService.saveLibrarian(admin.getId(), dto);
            //then
            assertEquals("Jean", jeanpaul.getFirstName());
            assertEquals("Paul", jeanpaul.getLastname());
            assertEquals("jean@paul2.fr", jeanpaul.getEmail());
            assertEquals("Rue", jeanpaul.getStreet());
            assertEquals("69", jeanpaul.getHouseNumber());
            assertEquals("Bxl", jeanpaul.getCity());
            assertEquals("1000", jeanpaul.getPostcode());
            assertNotNull(jeanpaul.getId());
        }

        @Test
        public void onlyAdminCanCreateLibrarian(){
            //given
            dto = new CreateUserDto("9998", "Jean", "Paul", "jean@paul2.fr", "Rue", "69", "Bxl", "1000");
            String randomId = UUID.randomUUID().toString();

            //then
            assertThrows(AuthorisationException.class, () -> userService.saveLibrarian(randomId, dto));
        }

        @Test
        public void ifEmailAddressExist_throwException(){
            CreateUserDto dto_1 = new CreateUserDto("9998", "Jean", "Paul", "jean@paul1.fr", "Rue", "69", "Bxl", "1000");
            CreateUserDto dto_2 = new CreateUserDto("9997", "Jean", "Paul", "jean@paul1.fr", "Rue", "69", "Bxl", "1000");

            // then
            userService.saveLibrarian(admin.getId(), dto_1);

            // when

            assertThrows(IllegalArgumentException.class,() -> userService.saveLibrarian(admin.getId(), dto_2));

        }

        @Test
        public void ifInnsExist_throwException(){
            CreateUserDto dto_1 = new CreateUserDto("9998", "Jean", "Paul", "jean@paul1.fr", "Rue", "69", "Bxl", "1000");
            CreateUserDto dto_2 = new CreateUserDto("9998", "Jean", "Paul", "jean@paul2.fr", "Rue", "69", "Bxl", "1000");

            // then
            userService.saveLibrarian(admin.getId(), dto_1);

            // when

            assertThrows(IllegalArgumentException.class,() -> userService.saveLibrarian(admin.getId(), dto_2));

        }

    }

    @Nested
    @DisplayName("Admin Tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TestSaveAdmin {

        //private User admin;

        @BeforeAll
        void setup() {
            // The hard coded librarian in the repo needs to be commented or removed
            //admin = new ArrayList<>(userRepository.getUsers()).get(0);
        }

        @Test
        public void whenSavingAdmin_returnAdmin(){
            //given
            dto = new CreateUserDto("9998", "Jean", "Paul", "jean@paul1.fr", "Rue", "69", "Bxl", "1000");
            //when
            UserDto jeanpaul = userService.saveAdmin(admin.getId(), dto);
            //then
            assertEquals("Jean", jeanpaul.getFirstName());
            assertEquals("Paul", jeanpaul.getLastname());
            assertEquals("jean@paul1.fr", jeanpaul.getEmail());
            assertEquals("Rue", jeanpaul.getStreet());
            assertEquals("69", jeanpaul.getHouseNumber());
            assertEquals("Bxl", jeanpaul.getCity());
            assertEquals("1000", jeanpaul.getPostcode());
            assertNotNull(jeanpaul.getId());
        }

        @Test
        public void onlyAdminCanCreateAdmin(){
            //given
            dto = new CreateUserDto("9998", "Jean", "Paul", "jean@paul1.fr", "Rue", "69", "Bxl", "1000");
            String randomId = UUID.randomUUID().toString();

            //then
            assertThrows(AuthorisationException.class, () -> userService.saveAdmin(randomId, dto));
        }

        @Test
        public void ifEmailAddressExist_throwException(){
            CreateUserDto dto_1 = new CreateUserDto("9998", "Jean", "Paul", "jean@paul1.fr", "Rue", "69", "Bxl", "1000");
            CreateUserDto dto_2 = new CreateUserDto("9997", "Jean", "Paul", "jean@paul1.fr", "Rue", "69", "Bxl", "1000");

            // then
            userService.saveAdmin(admin.getId(), dto_1);

            // when

            assertThrows(IllegalArgumentException.class,() -> userService.saveAdmin(admin.getId(), dto_2));

        }

        @Test
        public void ifInnsExist_throwException(){
            CreateUserDto dto_1 = new CreateUserDto("9998", "Jean", "Paul", "jean@paul1.fr", "Rue", "69", "Bxl", "1000");
            CreateUserDto dto_2 = new CreateUserDto("9998", "Jean", "Paul", "jean@paul2.fr", "Rue", "69", "Bxl", "1000");

            // then
            userService.saveAdmin(admin.getId(), dto_1);

            // when

            assertThrows(IllegalArgumentException.class,() -> userService.saveAdmin(admin.getId(), dto_2));

        }

    }


}