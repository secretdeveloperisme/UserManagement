package com.linhplus.UserManagement.services;

import com.linhplus.UserManagement.constants.PageConstant;
import com.linhplus.UserManagement.dto.PageableDTO;
import com.linhplus.UserManagement.dto.UserCreationDTO;
import com.linhplus.UserManagement.dto.UserDTO;
import com.linhplus.UserManagement.dto.UserUpdateDTO;
import com.linhplus.UserManagement.dto.response.ResponseObjectDTO;
import com.linhplus.UserManagement.exceptions.InputNotValidException;
import com.linhplus.UserManagement.exceptions.MyUsernameNotFoundException;
import com.linhplus.UserManagement.exceptions.UserNameExistedException;
import com.linhplus.UserManagement.mapper.ResponseMapper;
import com.linhplus.UserManagement.mapper.UserMapper;
import com.linhplus.UserManagement.models.User;
import com.linhplus.UserManagement.repositories.RoleRepository;
import com.linhplus.UserManagement.repositories.UserRepository;
import com.linhplus.UserManagement.repositories.specifications.SearchCriteria;
import com.linhplus.UserManagement.repositories.specifications.SearchOperator;
import com.linhplus.UserManagement.repositories.specifications.UserSpecification;
import com.linhplus.UserManagement.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final ResponseMapper responseMapper;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository
                       ,UserMapper userMapper, ResponseMapper responseMapper){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.responseMapper = responseMapper;
    }
    public ResponseEntity<ResponseObjectDTO> getUser(String username){
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()){
            UserDTO userDTO = userMapper.toDTO(userOptional.get());
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("user", userDTO);
            return ResponseEntity.ok(new ResponseObjectDTO("get user successfully ",responseData));
        }
        throw new MyUsernameNotFoundException("username is not found", username);
    }
    public ResponseEntity<ResponseObjectDTO> getAllUsers(String gender, String address, PageableDTO pageableDTO){
        UserSpecification userSpecification = new UserSpecification();
        if(gender != null && !gender.isEmpty()){
            userSpecification.getConditions().add(new SearchCriteria("gender", gender, SearchOperator.LIKE));
        }
        if(address != null && !address.isEmpty()){
            userSpecification.getConditions().add(new SearchCriteria("address", address, SearchOperator.LIKE));
        }
        List<Sort.Order> orders = new ArrayList<>();
        if(pageableDTO.getSort() != null){
            pageableDTO.getSort().forEach(s->{
                String[] sortParams = s.split("[\\-_\\.]");
                if(sortParams.length == PageConstant.SORT_ARGUMENT_SIZE){
                    if(sortParams[1].equalsIgnoreCase(PageConstant.DESCENDING)){
                        orders.add(Sort.Order.by(sortParams[0]).with(Sort.Direction.DESC));
                    }else
                        orders.add(Sort.Order.by(sortParams[0]).with(Sort.Direction.ASC));
                }
                else if(sortParams.length == 1){
                    orders.add(Sort.Order.by(sortParams[0]).with(Sort.Direction.ASC));
                }
            });
        }
        Page<User> userPage;
        Pageable pageable = PageRequest.of(pageableDTO.getPage(),pageableDTO.getSize(),Sort.by(orders));
        if(!userSpecification.getConditions().isEmpty()){
            userPage = userRepository.findAll(userSpecification, pageable);
        }
        else{
            userPage = userRepository.findAll(pageable);
        }
        return ResponseEntity.ok(responseMapper.toDTO(userPage));
    }
    public ResponseEntity<ResponseObjectDTO> createUser(UserCreationDTO userCreationDTO){
        User user = userMapper.toUser(userCreationDTO);
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if(!userOptional.isPresent()){
            user.setRoles(user.getRoles().stream().map(
                    role ->roleRepository.findByName(role.getName()).get()).collect(Collectors.toList()));
            user.setPassword(PasswordUtil.getInstance().hashPassword(user.getPassword()));
            User savedUser = userRepository.save(user);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("createdUser", savedUser);
            ResponseObjectDTO responseObjectDTO = new ResponseObjectDTO(
                    "create user successfully"
                    ,responseData);
            return ResponseEntity.ok(responseObjectDTO);
        }
        else {
            throw new UserNameExistedException("username is existed", user.getUsername());
        }
    }
    public ResponseEntity<ResponseObjectDTO> updateUser(UserUpdateDTO userUpdateDTO){
        User user = userMapper.toUser(userUpdateDTO);
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if(userOptional.isPresent()){
            User targetUser = userOptional.get();
            if(user.getPassword() != null){
                targetUser.setPassword(PasswordUtil.getInstance().hashPassword(user.getPassword()));
            }
            if(user.getDob() != null){
                targetUser.setDob(user.getDob());
            }
            if(user.getGender() != null){
                targetUser.setGender(user.getGender());
            }
            if(user.getAddress() != null){
                targetUser.setAddress(user.getAddress());
            }
            if(user.getRoles() != null && user.getRoles().size() > 0){
                targetUser.setRoles(user.getRoles().stream().map(role ->
                        roleRepository.findByName(role.getName()).get()).collect(Collectors.toList()));
            }
            User updatedUser =  userRepository.save(targetUser);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("updatedUser", updatedUser);
            ResponseObjectDTO responseObjectDTO = new ResponseObjectDTO(
                    "update user successfully"
                    ,responseData);
            return ResponseEntity.ok(responseObjectDTO);
        }
        else {
            throw new MyUsernameNotFoundException("username is not found", user.getUsername());
        }
    }
    public ResponseEntity<ResponseObjectDTO> deleteUsers(List<Long> ids) {
        List<Long> notExistIds = new ArrayList<>();
        List<Long> existedIds = userRepository.findExistedIds(ids);
        ids.forEach(id->{
            if(!existedIds.contains(id))
                notExistIds.add(id);
        });
        if(notExistIds.size() == 0){
            userRepository.deleteUsersByIds(ids);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("deletedIds", ids);
            ResponseObjectDTO responseObjectDTO = new ResponseObjectDTO(
                    "delete users successfully "
                    ,responseData);
            return ResponseEntity.ok(responseObjectDTO);
        }
        else{
            InputNotValidException inputNotValidException = new InputNotValidException("some ids is not found");
            inputNotValidException.getCauses().put("invalidIds", notExistIds);
            throw inputNotValidException;
        }
    }

}
