package com.UsersMVC.users.controllers;


import com.UsersMVC.users.models.User;
import com.UsersMVC.users.repositories.RoleRepository;
import com.UsersMVC.users.services.UserServiceImpl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/")

public class AdminController {

    private final UserServiceImpl userServiceImp;

    public final RoleRepository roleRepository;

    public AdminController(UserServiceImpl userRepository, RoleRepository roleRepository) {
        this.userServiceImp = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute("users", userServiceImp.index());// user добавляем атрибут в html

        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") String id, Model model) {
        if (id.equals("favicon.ico")) {
            return "redirect:/";
        }
        model.addAttribute("user", userServiceImp.show(Integer.parseInt(id)));
        return "users/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "users/new";
    }

    @PostMapping()
    public String creat(@ModelAttribute("user") @Valid User person,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "users/new";
        userServiceImp.save(person);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String edit( Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userServiceImp.show(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "users/edit";
        user.setId(id);
        userServiceImp.update(id, user);  // обновляем человека в базе даных который пришел на вход
        return "redirect:/";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        userServiceImp.delete(id);
        return "redirect:/";
    }


}

