package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.model.Product;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {
    @GetMapping("/customer")
    @RolesAllowed({"CUSTOMER"})
    public String customizedProductPage() {
        return "/customer-customized-product";
    }

    @PostMapping("/customer")
    @RolesAllowed({"CUSTOMER"})
    public String viewCustomizedProduct(){
        return "Hello world again SOS";
    }
}
