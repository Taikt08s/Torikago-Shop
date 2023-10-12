package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.service.ShoppingCartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingCartController {
    private ShoppingCartServices shoppingCartServices;

    @Autowired
    public ShoppingCartController(ShoppingCartServices shoppingCartServices) {
        this.shoppingCartServices = shoppingCartServices;
    }
    
    @GetMapping("/torikago/cart")
    public String showShoppingCart(Model model,
            @AuthenticationPrincipal Authentication authentication){
        return "shopping-cart";
    }
    
}
