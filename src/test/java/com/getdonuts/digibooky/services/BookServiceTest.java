package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.BookDto;
import com.getdonuts.digibooky.api.dto.BookWithDetailsDto;
import com.getdonuts.digibooky.api.dto.CreateUserDto;
import com.getdonuts.digibooky.api.dto.UserDto;
import com.getdonuts.digibooky.domain.Author;
import com.getdonuts.digibooky.domain.User;
import com.getdonuts.digibooky.exceptions.AuthorisationException;
import com.getdonuts.digibooky.repository.BookRepository;
import com.getdonuts.digibooky.repository.UserRepository;
import com.getdonuts.digibooky.services.mapper.BookMapper;
import com.getdonuts.digibooky.services.mapper.UserMapper;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookServiceTest {

    private BookService bookService;
    private BookRepository bookRepository;
    private UserService userService;
    private BookMapper bookMapper;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private User admin;
    BookWithDetailsDto bookToTestWith;
    UserDto librarian;
    UserDto member;


    @BeforeEach
    void setup() {
        bookMapper = new BookMapper();
        bookRepository = new BookRepository();
        userRepository = new UserRepository();
        userMapper = new UserMapper();
        userService = new UserService(userRepository, userMapper);
        bookService = new BookService(bookRepository, bookMapper, userService);
        admin = new ArrayList<>(userRepository.getUsers()).get(0);
        CreateUserDto dto = new CreateUserDto("9999", "Jean", "Paul", "jean@paul.fr", "Rue", "69", "Bxl", "1000");
        librarian = userService.saveLibrarian(admin.getId(), dto);
        dto = new CreateUserDto("8888", "Jean", "Luc", "jean@luc.fr", "Rue", "69", "Bxl", "1000");
        member = userService.saveMember(dto);
        bookToTestWith = new BookWithDetailsDto().setAuthor(new Author("blah", "blah")).setISBN("53543534").setTitle("Lord of the rings").setSummary("summary");
    }

    @Nested
    @DisplayName("Test of saving a book")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class saveBookTest {
        @Test
        void onlyLibrarianCanCreateBooks() {
            //when
            bookService.saveBook(bookToTestWith, librarian.getId());

            //then
            assertEquals("Lord of the rings", bookService.getBook("53543534").getTitle());

        }

        @Test
        void IfAdminCreatesBook_throwException() {

            //then
            assertThrows(AuthorisationException.class, () -> bookService.saveBook(bookToTestWith, admin.getId()));

        }

        @Test
        void IfMemberCreatesBook_throwException() {

            //then
            assertThrows(AuthorisationException.class, () -> bookService.saveBook(bookToTestWith, member.getId()));

        }

        @Test
        void ifNoISBN_throwException() {
            // when

            BookWithDetailsDto bookWithoutISBN = new BookWithDetailsDto().setAuthor(new Author("blah", "blah")).setISBN("").setTitle("Lord of the rings").setSummary("summary");

            // then
            assertThrows(IllegalArgumentException.class, () -> bookService.saveBook(bookWithoutISBN, librarian.getId()));
        }

        @Test
        void ifNoTitle_throwException() {
            // when

            BookWithDetailsDto bookWithoutTitle = new BookWithDetailsDto().setAuthor(new Author("blah", "blah")).setISBN("53543534").setTitle("").setSummary("summary");

            // then
            assertThrows(IllegalArgumentException.class, () -> bookService.saveBook(bookWithoutTitle, librarian.getId()));
        }

        @Test
        void ifNoAuthorLastName_throwException() {
            // when

            BookWithDetailsDto bookWithoutValidAuthor = new BookWithDetailsDto().setAuthor(new Author("blah", null)).setISBN("4355345").setTitle("Lord of t he rings").setSummary("summary");

            // then
            assertThrows(IllegalArgumentException.class, () -> bookService.saveBook(bookWithoutValidAuthor, librarian.getId()));
        }


    }

    @Nested
    @DisplayName("Test of geting a book")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class getBookTest {

        @Test
        void givenTitleRegex_returnCorrespondingBook() {
            String regexToUse = "Lord of the";
            bookService.saveBook(bookToTestWith, librarian.getId());

            assertTrue(bookService.getBookWithRegexTitle(regexToUse).stream().map(BookDto::getISBN).anyMatch((e) -> e.equals("53543534")));

        }

        @Test
        void givenISBNRegex_returnCorrespondingBook() {
            String regexToUse = "5354";
            bookService.saveBook(bookToTestWith, librarian.getId());

            assertTrue(bookService.getBookWithRegexIsbn(regexToUse).stream().map(BookDto::getTitle).anyMatch((e) -> e.equals("Lord of the rings")));

        }

        @Test
        void givenAuthor_returnCorrespondingBook() {
            bookService.saveBook(bookToTestWith, librarian.getId());

            assertTrue(bookService.getBookByAuthor("blah", "blah").stream().map(BookDto::getTitle).anyMatch((e) -> e.equals("Lord of the rings")));

        }

    }

    /*@Nested
    @DisplayName("Test of deleting a book")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class deleteBookTest {

        @Test
        void ifBookSoftDeleted_serviceThrowsException() {
            bookService.saveBook(bookToTestWith, librarian.getId());
            bookService.toggleDeleteBook(bookToTestWith.getISBN(), librarian.getId());

            assertThrows(IllegalArgumentException.class, () -> bookService.getBook(bookToTestWith.getISBN()));

        }
    }*/
}