package com.linhplus.UserManagement.controlllers;

import com.linhplus.UserManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SiteController {
    @Autowired
    UserService userService;

    @ResponseBody
    @GetMapping(path = "/")
    public String index(){
        return "index";
    }

    @GetMapping(path = "/auth/login")
    public String login(){
        return "auth/login";
    }
}
