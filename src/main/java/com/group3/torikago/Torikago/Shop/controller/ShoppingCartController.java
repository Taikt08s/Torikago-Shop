package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.CustomizedBirdCage;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.service.*;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.service.ProductService;
import com.group3.torikago.Torikago.Shop.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.group3.torikago.Torikago.Shop.service.ShoppingCartService;

@Controller
public class ShoppingCartController {
    private ShoppingCartService shoppingCartServices;
    private UserService userService;
    private ProductService productService;
    private CustomizedBirdCageService customizedBirdCageService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService,
                                  CustomizedBirdCageService customizedBirdCageService, ProductService productService) {
        this.shoppingCartServices = shoppingCartService;
        this.userService = userService;
        this.customizedBirdCageService = customizedBirdCageService;
        this.productService = productService;
    }

    @GetMapping("/torikago/cart")
    public String showShoppingCart(Model model,
                                   @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails) {
        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);
        List<CartItems> cartItems = shoppingCartServices.listCart(user);
        model.addAttribute("cartItems", cartItems);
        return "shopping-cart";
    }

    @PostMapping("/cart/add")
    public String addProductToCart(@RequestParam("productId") Long productId,
                                   @RequestParam("quantity") int quantity,
                                   @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails) {
        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);

        int error = shoppingCartServices.addProduct(productId, quantity, user);
        if (error == 0) {
            return "redirect:/torikago/product/" + productId + "?error";
        } else {
            return "redirect:/torikago/product/" + productId + "?success";
        }
    }

    @PostMapping("/cart/update")
    public String updateQuantity(@RequestParam("productId") Long productId,
                                 @RequestParam("quantity") int quantity,
                                 @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails) {
        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);
        shoppingCartServices.updateQuantity(productId, quantity, user);
        return "redirect:/torikago/cart";
    }

    @PostMapping("/cart/delete")
    public String removeProduct(@RequestParam("productId") Long productId,
                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails) {
        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);
        shoppingCartServices.removeProduct(productId, user);
        return "redirect:/torikago/cart";
    }

    @GetMapping("/torikago/checkout")
    public String checkOut(@AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails, Model model) {
        String email = myUserDetails.getUsername();
        User user = userService.findByEmail(email);
        List<CartItems> cartItems = shoppingCartServices.listCartItems(user);
        String errorUrl = "";
        for (CartItems cartItem : cartItems) {
            Product product = productService.findProductById(cartItem.getProductId().getId());
            if (product.getUnitsInStock() < cartItem.getQuantity()) {
                errorUrl = errorUrl + "&q" + product.getId() + "=" + product.getUnitsInStock();
            }
        }
        if (errorUrl.isEmpty()) {
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("user", user);
            return "shopping-view-order";
        } else {
            return "redirect:/torikago/cart?error" + errorUrl;
        }
    }
}
