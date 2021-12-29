package com.mediasoft.services.demo.controllers;

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

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("user-form")
    public String user_form(Model model) {
        model.addAttribute("userList", service.getAllUsers());
        return "user-form/user-view";
    }
}
