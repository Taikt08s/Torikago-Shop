package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.service.ShoppingCartServices;
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
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails){
        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);
        List<CartItems> cartItems = shoppingCartServices.listCartItems(user);
        model.addAttribute("cartItems", cartItems);
        return "shopping-cart";
    }
    @PostMapping("/cart/add")
    public String addProductToCart(@RequestParam("productId") Long productId,
            @RequestParam("quantity") int quantity,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails) {
        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);
        shoppingCartServices.addProduct(productId, quantity, user);
        return "redirect:/torikago/product/" + productId+"?success=true";
    }
//        @PostMapping(value = "/cart/add/{pid}/{qty}", produces = "text/html")
//        public String addProductToCart(@PathVariable("pid") Long productId,
//                @PathVariable("qty") int quantity, 
//                @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails){
//            if(myUserDetails == null) return "You must login to continue.";
//            String userName = myUserDetails.getUsername();
//            User user = userService.findByEmail(userName);
//            int addedQuantity = shoppingCartServices.addProduct(productId, quantity, user);
//            return addedQuantity + "item(s) of this product were added to your cart";
//        }
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
        model.addAttribute("user", user);
        return "shopping-view-order";
    }
}
