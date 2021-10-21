package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.CreateUserDto;
import com.getdonuts.digibooky.api.dto.UserDto;
import com.getdonuts.digibooky.domain.User;
import com.getdonuts.digibooky.exceptions.AuthorisationException;
import com.getdonuts.digibooky.repository.UserRepository;
import com.getdonuts.digibooky.services.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository repo;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository repo, UserMapper userMapper) {
        this.repo = repo;
        this.userMapper = userMapper;
    }

    public Collection<UserDto> getAll(String id) {
        if (!validateAdmin(id)) {
            throw new IllegalArgumentException("User with ID number " + id + " has no admin rights to perform this action.");
        }
        return userMapper.toDTO(repo.getUsers());
    }

    private User createMember(CreateUserDto DTO) {
        if (isINSSunique(DTO.getInss()) && validateMail(DTO.getEmail()) && validateInputs(DTO.getLastName()) && validateInputs(DTO.getCity()))
            return userMapper.toMember(DTO);
        else
            throw new IllegalArgumentException("Inputs were not valid");
    }

    private boolean isEmailUnique(String email) {
        List<String> allMemberEmails = repo.getUsers().stream()
                .map(User::getEmail)
                .collect(Collectors.toList());

        if (allMemberEmails.contains(email)) {
            throw new IllegalArgumentException("This e-mail is already used.");
        }
        return true;

    }

    private boolean isEmailValid(String email) {
        if (email == null || email.isEmpty() || email.isBlank())
            return false;

        Pattern regex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(email);

        if (matcher.find())
            return true;
        else
            throw new IllegalArgumentException("This e-mail is not valid.");
    }

    private boolean isINSSunique(String inss) {
        List<String> allUserInss = repo.getUsers().stream()
                .map(User::getINSS)
                .collect(Collectors.toList());
        if (allUserInss.contains(inss)) {
            throw new IllegalArgumentException("INSS is already used.");
        }
        return true;
    }

    private boolean validateMail(String email) {
        return isEmailValid(email) && isEmailUnique(email);
    }

    private boolean validateInputs(String string) {
        return string != null && !string.isEmpty() && !string.isBlank();
    }

    public UserDto saveMember(CreateUserDto createUserDTO) {
        User createdUser = createMember(createUserDTO);
        return userMapper.toDTO(repo.addMember(createdUser));
    }

    public boolean validateAdmin(String id) {
        var list = repo.getUsers().stream()
                .filter(User::isAdmin)
                .map(User::getId)
                .collect(Collectors.toList());

        return list.contains(id);
    }

    public UserDto saveLibrarian(String id, CreateUserDto dto) {
        if(validateAdmin(id)) {
            User createdUser = createMember(dto);
            createdUser.setLibrarian(true);
            return userMapper.toDTO(repo.addMember(createdUser));
        }
        throw new AuthorisationException("Admin rights necessary");
    }

    public UserDto saveAdmin(String id, CreateUserDto dto) {
        if(validateAdmin(id)) {
            User createdUser = createMember(dto);
            createdUser.setAdmin(true);
            return userMapper.toDTO(repo.addMember(createdUser));
        }
        throw new AuthorisationException("Admin rights necessary");
    }
}