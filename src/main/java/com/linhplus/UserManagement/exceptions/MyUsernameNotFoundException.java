package com.linhplus.UserManagement.exceptions;

import lombok.Getter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Getter
public class MyUsernameNotFoundException extends UsernameNotFoundException {
    private String username;
    public MyUsernameNotFoundException(String message, String username){
        super(message);
        this.username = username;
    }
}
