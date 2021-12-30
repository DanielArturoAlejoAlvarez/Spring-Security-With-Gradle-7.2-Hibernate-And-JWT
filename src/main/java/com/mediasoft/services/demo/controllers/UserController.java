package com.mediasoft.services.demo.controllers;

import com.mediasoft.services.demo.entities.User;
import com.mediasoft.services.demo.repositories.IRole;
import com.mediasoft.services.demo.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/userForm")
    public String userForm(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("userList", service.getAllUsers());
        model.addAttribute("roles", roleRepo.findAll());
        model.addAttribute("listTab", "active");
        return "user-form/user-view";
    }

    @PostMapping("/userForm")
    public String createUser(@Valid @ModelAttribute("userForm") User user, BindingResult result, ModelMap model) {
        user.setStatus(true);
        if (result.hasErrors()) {
            model.addAttribute("userForm", user);
            model.addAttribute("formTab", "active");
        }else {
            try {
                service.createUser(user);
                model.addAttribute("userForm", new User());
                model.addAttribute("listTab", "active");
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("userForm", user);
                model.addAttribute("formTab", "active");
                model.addAttribute("userList", service.getAllUsers());
                model.addAttribute("roles", roleRepo.findAll());
                //e.printStackTrace();
            }
        }
        model.addAttribute("userList", service.getAllUsers());
        model.addAttribute("roles", roleRepo.findAll());
        return "user-form/user-view";
    }

    @GetMapping("/editUser/{id}")
    public String getEditUserForm(Model model, @PathVariable(name = "id") Long id) throws Exception {
        User userToEdit = service.findById(id);
        model.addAttribute("userForm", userToEdit);
        model.addAttribute("formTab", "active");
        model.addAttribute("userList", service.getAllUsers());
        model.addAttribute("roles", roleRepo.findAll());

        model.addAttribute("editMode", "true");
        return "user-form/user-view";
    }

    @PostMapping("/updateUser")
    public String postEditUserForm(@Valid @ModelAttribute("userForm") User user, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("userForm", user);
            model.addAttribute("formTab", "active");
            model.addAttribute("editMode", "true");
        }else {
            try {
                service.updateUser(user);
                model.addAttribute("userForm", new User());
                model.addAttribute("listTab", "active");
                model.addAttribute("editMode", "false");
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("userForm", user);
                model.addAttribute("formTab", "active");
                model.addAttribute("userList", service.getAllUsers());
                model.addAttribute("roles", roleRepo.findAll());

                model.addAttribute("editMode", "true");
                //e.printStackTrace();
            }
        }

        model.addAttribute("userList", service.getAllUsers());
        model.addAttribute("roles", roleRepo.findAll());
        return "user-form/user-view";
    }

    @GetMapping("/userForm/cancel")
    public String cancelEditUser(ModelMap modelMap) {
        return "redirect:/userForm";
    }
}
