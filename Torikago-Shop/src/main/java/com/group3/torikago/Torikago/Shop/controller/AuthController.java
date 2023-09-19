package com.group3.torikago.Torikago.Shop.controller;


import com.group3.torikago.Torikago.Shop.service.UserService;
import org.springframework.stereotype.Controller;


import java.util.List;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }





}
