package com.mediasoft.services.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("user-form")
    public String user_form() {
        return "user-form/user-view";
    }
}
