package com.linhplus.UserManagement.mapper;

import com.linhplus.UserManagement.dto.UserDTO;
import com.linhplus.UserManagement.dto.response.ResponseObjectDTO;
import com.linhplus.UserManagement.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ResponseMapper {
    @Autowired UserMapper userMapper;
    public ResponseObjectDTO toDTO(Page<User> userPage){
        List<UserDTO> usersDTO = userPage.getContent().stream().map(userMapper::toDTO).collect(Collectors.toList());
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("users", usersDTO);
        responseData.put("totalPages", userPage.getTotalPages());
        responseData.put("size", userPage.getSize());
        responseData.put("totalElements", userPage.getTotalElements());
        responseData.put("page", userPage.getNumber());
        responseData.put("numberOfElements", userPage.getNumberOfElements());
        return new ResponseObjectDTO("get all users successfully",responseData);
    }
}
