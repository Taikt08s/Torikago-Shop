package com.group3.torikago.Torikago.Shop.controller;

<<<<<<< HEAD

import com.group3.torikago.Torikago.Shop.service.UserService;
=======
import com.group3.torikago.Torikago.Shop.dto.RegisterDTO;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.service.UserService;
import jakarta.validation.Valid;
>>>>>>> b30e9d69f466fba3d77974c918771e4e0ee9317b
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

<<<<<<< HEAD




=======
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegisterDTO user = new RegisterDTO();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegisterDTO user,
                           BindingResult result, Model model) {
        User existingUserEmail = userService.findByEmail(user.getEmail());
        if (existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }
        User existingUserUsername = userService.findByUsername(user.getUserName());
        if (existingUserUsername != null && existingUserUsername.getUserName() != null && !existingUserUsername.getUserName().isEmpty()) {
            return "redirect:/register?fail";
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/torikago?success";
    }
>>>>>>> b30e9d69f466fba3d77974c918771e4e0ee9317b
}
