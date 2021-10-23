package com.getdonuts.digibooky.services.mapper;

import com.getdonuts.digibooky.api.dto.CreateUserDto;
import com.getdonuts.digibooky.api.dto.UserDto;
import com.getdonuts.digibooky.domain.Address;
import com.getdonuts.digibooky.domain.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toUser(CreateUserDto DTO){
        return new User(DTO.getInss(), DTO.getFirstName(), DTO.getLastName(), DTO.getEmail(), new Address(DTO.getStreet(), DTO.getHouseNumber(), DTO.getPostcode(), DTO.getCity()));
    }

    public UserDto toDTO(User user){
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getAddress().getStreet(), user.getAddress().getHouseNumber(), user.getAddress().getPostalCode(), user.getAddress().getCity());
    }

    public Collection<UserDto> toDTO(Collection<User> users){
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
