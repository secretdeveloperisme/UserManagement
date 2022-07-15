package com.linhplus.ManagingUser;

import com.linhplus.UserManagement.models.User;
import com.linhplus.UserManagement.repositories.UserRepository;
import com.linhplus.UserManagement.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @MockBean
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Test
    public void getAllUserTest(){
        Mockito.when(userRepository.findAll()).thenReturn(List.of(new User(1L, "hoanglinh", " ", "m",Date.valueOf("2000-11-25"), "kiengiang",null)));

        userRepository.findAll().forEach(user -> {
            log.info("user: {}", user.toString());
        });
        userService.getAllUsers(null, null, null);
    }
}
