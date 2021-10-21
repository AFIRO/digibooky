package com.getdonuts.digibooky.api;

import com.getdonuts.digibooky.api.dto.CreateUserDto;
import com.getdonuts.digibooky.api.dto.UserDto;
import com.getdonuts.digibooky.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/members")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createMember(@RequestBody CreateUserDto DTO) {
        return userService.saveMember(DTO);
    }

    @PostMapping(produces = "application/json", consumes = "application/json", path = "/librarian/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createLibrarian(@PathVariable String id, @RequestBody CreateUserDto DTO) {
        return userService.saveLibrarian(id, DTO);
    }

    @PostMapping(produces = "application/json", consumes = "application/json", path = "/admin/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createAdmin(@PathVariable String id, @RequestBody CreateUserDto DTO) {
        return userService.saveAdmin(id, DTO);
    }

    @GetMapping(produces = "application/json", path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDto> getAll(@PathVariable String id){
        return userService.getAll(id);
    }
}
