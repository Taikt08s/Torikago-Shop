package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.dto.RegisterDTO;
import com.group3.torikago.Torikago.Shop.model.Role;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.service.UserService;
import com.group3.torikago.Torikago.Shop.util.Util;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
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
                           BindingResult result, Model model, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
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
        String siteURL = Util.getSiteURL(request);
        User currentUser = userService.findByEmail(user.getEmail());
        userService.sendVerificationEmail( siteURL, currentUser);
        return "redirect:/torikago?success";
    }
    @GetMapping("/verify")
    public String verifyAuthentication(@RequestParam("code") String code) {
        if (userService.verify(code)) {
            return "verification-success";
        } else {
            return "verification-fail";
        }
    }
//    @GetMapping("/users")
//    public String listUsers(Model model) {
//        List<User> listUsers = UserService.listAll();
//        model.addAttribute("listUsers", listUsers);
//
//        return "users";
//    }
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model){
        User user = userService.get(id);
        List<Role> listRoles = userService.listRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user-form";
    }


}
