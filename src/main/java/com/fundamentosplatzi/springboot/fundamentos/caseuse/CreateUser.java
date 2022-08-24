package com.fundamentosplatzi.springboot.fundamentos.caseuse;

import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class CreateUser {
    private final UserService userService;

    public CreateUser(UserService UserService) {
        this.userService = UserService;
    }

    public User save(User newUser) {
        return userService.save(newUser);
    }
}
