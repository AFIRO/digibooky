package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.CreateUserDto;
import com.getdonuts.digibooky.api.dto.UserDto;
import com.getdonuts.digibooky.domain.User;
import com.getdonuts.digibooky.exceptions.AuthorisationException;
import com.getdonuts.digibooky.repository.UserRepository;
import com.getdonuts.digibooky.services.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository repo;
    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository repo, UserMapper userMapper) {
        this.repo = repo;
        this.userMapper = userMapper;
    }

    public Collection<UserDto> getAll(String id) {
        if (!validateAdmin(id)) {
            throw new AuthorisationException("User with ID number " + id + " has no admin rights to perform this action.");
        }
        return userMapper.toDTO(repo.getUsers());
    }

    public boolean userExists(String id){
        return repo.getUsers()
                .stream()
                .anyMatch(user -> user.getId().equals(id));
    }

    private User createUser(CreateUserDto DTO) {
        if (isINSSValid(DTO.getInss()) && validateMail(DTO.getEmail()) && validateLastName(DTO.getLastName()) && validateCity(DTO.getCity())) {
            logger.info("User created");
            return userMapper.toUser(DTO);
        }
        else
            throw new IllegalArgumentException("Inputs were not valid");
    }

    private boolean isINSSValid(String inss) {
        if(!isGiven(inss)){
            throw new IllegalArgumentException("inss can not be blank or empty");
        }
        return isINSSunique(inss);
    }

    private boolean isGiven(String input) {
        return input != null && !input.isEmpty() && !input.isBlank();
    }

    private boolean isEmailUnique(String email) {
        List<String> allMemberEmails = repo.getUsers().stream()
                .map(User::getEmail)
                .collect(Collectors.toList());

        if (allMemberEmails.contains(email)) {
            throw new IllegalArgumentException("This e-mail : " + email + " is already used.");
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        if (email == null || email.isEmpty() || email.isBlank())
            throw new IllegalArgumentException("Email can not be empty.");

        Pattern regex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(email);

        if (matcher.find())
            return true;
        else
            throw new IllegalArgumentException("This e-mail : " + email  +" is not valid.");
    }

    private boolean isINSSunique(String inss) {
        List<String> allUserInss = repo.getUsers().stream()
                .map(User::getINSS)
                .collect(Collectors.toList());
        if (allUserInss.contains(inss)) {
            throw new IllegalArgumentException("INSS : " + inss + "  is already used.");
        }
        return true;
    }

    private boolean validateMail(String email) {
        return isEmailValid(email) && isEmailUnique(email);
    }

    private boolean validateLastName(String input) {
        if(!isGiven(input)){
            throw new IllegalArgumentException("Last name can not be empty");
        }
        return true;
    }

    private boolean validateCity(String input) {
        if(!isGiven(input)){
            throw new IllegalArgumentException("City can not be empty.");
        }
        return true;
    }

    public boolean validateAdmin(String id) {
        var list = repo.getUsers().stream()
                .filter(User::isAdmin)
                .map(User::getId)
                .collect(Collectors.toList());

        return list.contains(id);
    }

    public boolean validateLibrarian(String id) {
        var list = repo.getUsers().stream()
                .filter(User::isLibrarian)
                .map(User::getId)
                .collect(Collectors.toList());

        return list.contains(id);
    }

    public boolean validateMember(String id) {
        var list = repo.getUsers().stream()
                .filter(User::isMember)
                .map(User::getId)
                .collect(Collectors.toList());

        return list.contains(id);
    }

    public UserDto saveMember(CreateUserDto dto) {
        User createdUser = createUser(dto);
        createdUser.setMember(true);
        logger.info("User saved as Member");
        return userMapper.toDTO(repo.addUser(createdUser));
    }

    public UserDto saveLibrarian(String id, CreateUserDto dto) {
        if(validateAdmin(id)) {
            User createdUser = createUser(dto);
            createdUser.setLibrarian(true);
            logger.info("User saved as Librarian by " + id);
            return userMapper.toDTO(repo.addUser(createdUser));
        }
        throw new AuthorisationException("User : " + id + " without admin rights tried to add a librarian");
    }

    public UserDto saveAdmin(String id, CreateUserDto dto) {
        if(validateAdmin(id)) {
            User createdUser = createUser(dto);
            createdUser.setAdmin(true);
            logger.info("User saved as Admin by " + id);
            return userMapper.toDTO(repo.addUser(createdUser));
        }
        throw new AuthorisationException("User : " + id + " without admin rights tried to add an admin");
    }
}
