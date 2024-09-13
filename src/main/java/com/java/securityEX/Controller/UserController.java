package com.java.securityEX.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.securityEX.Services.UserService;
import com.java.securityEX.model.User;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return service.verify(user);
    }
}
