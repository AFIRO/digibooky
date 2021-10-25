package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.CreateUserDto;
import com.getdonuts.digibooky.api.dto.UserDto;
import com.getdonuts.digibooky.domain.User;
import com.getdonuts.digibooky.repository.UserRepository;
import com.getdonuts.digibooky.services.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private CreateUserDto dto;

    @BeforeAll
    void setup() {
        userRepository = new UserRepository();
        userMapper = new UserMapper();
        userService = new UserService(userRepository, userMapper);
    }

    @Test
    void testSaveMember() {
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
    void whenNoInss_shouldThrowException(){
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

    @Nested
    @DisplayName("Librarian Tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TestSaveLibrarian {

        private User admin;

        @BeforeAll
        void setup() {
            // The hard coded librarian in the repo needs to be commented or removed
            admin = new ArrayList<>(userRepository.getUsers()).get(0);
        }

        @Test
        public void whenSavingLibrarian_returnLibrarian(){
            //given
            dto = new CreateUserDto("9998", "Jean", "Paul", "jean@paul1.fr", "Rue", "69", "Bxl", "1000");
            //when
            UserDto jeanpaul = userService.saveLibrarian(admin.getId(), dto);
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

    }


}