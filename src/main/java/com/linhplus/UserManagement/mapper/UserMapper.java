package com.linhplus.UserManagement.mapper;

import com.linhplus.UserManagement.dto.UserCreationDTO;
import com.linhplus.UserManagement.dto.UserDTO;
import com.linhplus.UserManagement.dto.UserUpdateDTO;
import com.linhplus.UserManagement.models.Role;
import com.linhplus.UserManagement.models.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User toUser(UserCreationDTO userCreationDTO){
        User user = new User();
        user.setUsername(userCreationDTO.getUsername());
        user.setPassword(userCreationDTO.getPassword());
        user.setDob(userCreationDTO.getDob());
        user.setGender(userCreationDTO.getGender());
        user.setAddress(userCreationDTO.getAddress());
        user.setRoles(
                userCreationDTO.getRoles().stream().map(role->new Role(null, role)).collect(Collectors.toList())
        );
        return user;
    }
    public User toUser(UserUpdateDTO userUpdateDTO){
        User user = new User();
        user.setUsername(userUpdateDTO.getUsername());
        user.setPassword(userUpdateDTO.getPassword());
        user.setDob(userUpdateDTO.getDob());
        user.setGender(userUpdateDTO.getGender());
        user.setAddress(userUpdateDTO.getAddress());
        if(user.getRoles() != null)
        user.setRoles(
                userUpdateDTO.getRoles().stream().map(role->new Role(null, role)).collect(Collectors.toList())
        );
        return user;
    }
    public UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setDob(user.getDob());
        userDTO.setGender(user.getGender());
        userDTO.setAddress(user.getAddress());
        userDTO.setRoles(
                user.getRoles().stream().map(role->role.getName()).collect(Collectors.toList())
        );
        return userDTO;
    }
}
