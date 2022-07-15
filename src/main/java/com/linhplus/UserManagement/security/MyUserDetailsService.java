package com.linhplus.UserManagement.security;

import com.linhplus.UserManagement.exceptions.MyUsernameNotFoundException;
import com.linhplus.UserManagement.repositories.UserRepository;
import com.linhplus.UserManagement.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Slf4j
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        UserDetails userDetails = null;
        com.linhplus.UserManagement.models.User user = null;
        Optional<com.linhplus.UserManagement.models.User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()){
            user = userOptional.get();
            List<GrantedAuthority> roles = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
            userDetails = new User(user.getUsername(), user.getPassword(), roles);
            return userDetails;
        }
        else{
            throw new UsernameNotFoundException("username is not found");
        }


    }
}

