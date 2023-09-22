package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.service.ProductService;
import jakarta.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {
    @GetMapping("/admin")
    @RolesAllowed({"ADMIN"})
    public String adminPage(){
        return "admin-dashboard";
    }

    private ProductService productService;
    @Autowired
    public AdminController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/admin/product-table")
    @RolesAllowed({"ADMIN"})
    public String getListProduct(Model model){
        List<ProductDTO> products=productService.findAllProducts();
        model.addAttribute("products",products);
        return "admin-product";
    }

    @GetMapping("/admin/product-table/bird-cage/add")
    @RolesAllowed({"ADMIN"})
    public String addBirdCage(Model model){
        Product product=new Product();
        model.addAttribute("product",product);
        return "bird-cage";
    }
    @PostMapping("/admin/product-table/bird-cage/add")
    public String saveBirdcage(@ModelAttribute("product") Product product){
        productService.saveBirdCage(product);
        return "redirect:/admin";
    }


    //    @GetMapping("/Bird-cage Product/{productID}/edit")
//    public String editBirdCage(@PathVariable("productID")long productID, Model model){
//        Bird_CageDTO birdCage=productService.findProductById(productID);
//        model.addAttribute("birdCage",birdCage);
//        return "Bird-cage-edit";
//    }
    @PostMapping("/admin/product-table/{productID}/edit")
    public String updateProduct(@PathVariable("productID")Long productId, @ModelAttribute("product") ProductDTO product){
        product.setProductId(productId);
        productService.updateProduct(product);
        return "redirect:/admin";
    }

}

