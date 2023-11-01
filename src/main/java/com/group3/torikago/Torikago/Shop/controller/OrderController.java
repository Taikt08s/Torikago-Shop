package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.service.ShoppingCartService;
import com.group3.torikago.Torikago.Shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrderController {
    private UserService userService;

    private ShoppingCartService shoppingCartServices;

    @Autowired
    public OrderController(UserService userService, ShoppingCartService shoppingCartServices) {
        this.userService = userService;
        this.shoppingCartServices = shoppingCartServices;
    }

    @GetMapping("/user/purchase")
    public String showUserOrder(Model model,
                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails) {
        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);
        List<CartItems> cartItems = shoppingCartServices.listCart(user);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("user", user);
        return "shopping-user-order";
    }

}
