package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.model.Role;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.service.ShoppingCartServices;
import com.group3.torikago.Torikago.Shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShoppingCartController {
    private ShoppingCartServices shoppingCartServices;
    private UserService userService;

    @Autowired
    public ShoppingCartController(ShoppingCartServices shoppingCartServices, UserService userService) {
        this.shoppingCartServices = shoppingCartServices;
        this.userService = userService;
    }

    @GetMapping("/torikago/cart")
    public String showShoppingCart(Model model,
                                   @AuthenticationPrincipal Authentication authentication) {
        return "shopping-cart";
    }


    @GetMapping("/torikago/checkout")
    public String showShoppingCart(@AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails, Model model) {
        String email = myUserDetails.getUsername();
        User user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "shopping-view-order";
    }
}
