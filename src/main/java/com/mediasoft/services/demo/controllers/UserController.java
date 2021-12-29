package com.mediasoft.services.demo.controllers;

import com.mediasoft.services.demo.entities.User;
import com.mediasoft.services.demo.repositories.IRole;
import com.mediasoft.services.demo.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private IUserService service;

    @Autowired
    private IRole roleRepo;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("user-form")
    public String user_form(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("userList", service.getAllUsers());
        model.addAttribute("roles", roleRepo.findAll());
        model.addAttribute("listTab", "active");
        return "user-form/user-view";
    }

    
}
