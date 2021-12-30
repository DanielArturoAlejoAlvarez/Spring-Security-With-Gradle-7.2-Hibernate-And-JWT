package com.mediasoft.services.demo.controllers;

import com.mediasoft.services.demo.repositories.IRole;
import com.mediasoft.services.demo.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private IRoleService service;


}
