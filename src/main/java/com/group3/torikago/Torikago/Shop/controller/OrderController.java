package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.Order;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.service.OrderService;
import com.group3.torikago.Torikago.Shop.service.ShoppingCartService;
import com.group3.torikago.Torikago.Shop.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {
    private UserService userService;

    private OrderService orderService;

    private ShoppingCartService shoppingCartServices;

    @Autowired
    public OrderController(UserService userService, OrderService orderService, ShoppingCartService shoppingCartServices) {
        this.userService = userService;
        this.orderService = orderService;
        this.shoppingCartServices = shoppingCartServices;
    }

    @GetMapping("/user/purchase")
    public String showUserOrder(Model model,
                                @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails) {
        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);
        List<Order> orders = orderService.listOrdersByUser(user);
        List<CartItems> cartItems = shoppingCartServices.listCartItems(user);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("orders", orders);
        model.addAttribute("user", user);
        return "shopping-order-history";
    }

    @GetMapping("/manager/orders")
    @RolesAllowed({"MANAGER"})
    public String showOrderList(Model model,
                                @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                @RequestParam(name = "sortField", defaultValue = "orderId") String sortField,
                                @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {
        Page<Order> orders = orderService.findPaginatedOrders(pageNumber, pageSize, sortField, sortDir);
        model.addAttribute("orders", orders);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        return "manager-order";
    }
}
