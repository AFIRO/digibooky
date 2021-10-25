package com.getdonuts.digibooky.api;

import com.getdonuts.digibooky.api.dto.CreateUserDto;
import com.getdonuts.digibooky.api.dto.UserDto;
import com.getdonuts.digibooky.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/members")
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json" ,path = "/member/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createMember(@PathVariable String id ,@RequestBody CreateUserDto DTO) {
        logger.info("createMember() called");
        return userService.saveMember(id, DTO);
    }

    @PostMapping(produces = "application/json", consumes = "application/json", path = "/librarian/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createLibrarian(@PathVariable String id, @RequestBody CreateUserDto DTO) {
        logger.info("createLibrarian() called");
        return userService.saveLibrarian(id, DTO);
    }

    @PostMapping(produces = "application/json", consumes = "application/json", path = "/admin/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createAdmin(@PathVariable String id, @RequestBody CreateUserDto DTO) {
        logger.info("createAdmin() called");
        return userService.saveAdmin(id, DTO);
    }

    @GetMapping(produces = "application/json", path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDto> getAllUsers(@PathVariable String id){
        logger.info("getAllUsers() called");
        return userService.getAll(id);
    }
}
