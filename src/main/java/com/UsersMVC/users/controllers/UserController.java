package com.UsersMVC.users.controllers;

import com.UsersMVC.users.repositories.UserRepository;
import com.UsersMVC.users.services.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
@Controller
@RequestMapping("/user")
public class UserController {

private final UserServiceImpl userRepository;

    public UserController(UserServiceImpl userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping()
    public String show(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        return "users/user";
    }

}
