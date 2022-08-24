package com.fundamentosplatzi.springboot.fundamentos.caseuse;

import com.fundamentosplatzi.springboot.fundamentos.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class DeleteUser {
    private final UserService userService;

    public DeleteUser(UserService UserService) {
        this.userService = UserService;
    }

    public void remove(Long id) {
        userService.delete(id);
    }
}
