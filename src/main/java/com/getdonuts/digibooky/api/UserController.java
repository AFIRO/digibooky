package com.getdonuts.digibooky.api;

import com.getdonuts.digibooky.api.dto.CreateUserDto;
import com.getdonuts.digibooky.api.dto.UserDto;
import com.getdonuts.digibooky.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection; // CODEREVIEW why collection? (2x)

@RestController
@RequestMapping(path = "/members")
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createMember(@RequestBody CreateUserDto DTO) {
        logger.info("createMember() called");
        return userService.saveMember(DTO);
    }

    // CODEREVIEW a slightly more REST-like style is possible
    // POST /librarians (so not /users/librarians, yes this is allowed)
    // PUT /users/{userId} with {librarian:true} in the requestbody
    @PostMapping(produces = "application/json", consumes = "application/json", path = "/librarian/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createLibrarian(@PathVariable String id, @RequestBody CreateUserDto DTO) {
        // CODEREVIEW
        // what does `id` mean? This variable name is not descriptive
        // Suggestion: Add the admin or librarian id to the headers with @RequestHeader
        // Suggestion: Set the `id` parameter as last parameter
        logger.info("createLibrarian() called");
        return userService.saveLibrarian(id, DTO);
    }

    // CODEREVIEW a slightly more REST-like style is possible
    // Same as above
    @PostMapping(produces = "application/json", consumes = "application/json", path = "/admin/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createAdmin(@PathVariable String id, @RequestBody CreateUserDto DTO) {
        // CODEREVIEW
        // what does `id` mean? This variable name is not descriptive
        // Suggestion: Add the admin or librarian id to the headers with @RequestHeader
        // Suggestion: Set the `id` parameter as last parameter
        logger.info("createAdmin() called");
        return userService.saveAdmin(id, DTO);
    }

    @GetMapping(produces = "application/json", path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDto> getAllUsers(@PathVariable String id) {
        // CODEREVIEW
        // what does `id` mean? This variable name is not descriptive
        // Suggestion: Add the admin or librarian id to the headers with @RequestHeader
        logger.info("getAllUsers() called");
        return userService.getAll(id);
    }
}
