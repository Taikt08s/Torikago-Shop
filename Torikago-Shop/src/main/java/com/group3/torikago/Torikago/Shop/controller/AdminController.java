package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.DTO.ProductDTO;
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


    @GetMapping("/admin/product-table")
    @RolesAllowed({"ADMIN"})
    public String getListProduct(){
        return "admin-product";
    }

    private ProductService productService;
    @Autowired
    public AdminController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/products")
    public String listBirdCage(Model model){
        List<ProductDTO> products=productService.findAllProducts();
        model.addAttribute("products",products);
        return "products-list";
    }
    @GetMapping("/products/new")
    public String createBirdcage(Model model){
        Product product=new Product();
        model.addAttribute("product",product);
        return "products-create";
    }
    @PostMapping("/products/new")
    public String saveBirdcage(@ModelAttribute("product") Product product){
        productService.saveBirdCage(product);
        return "redirect:/product";
    }


    //    @GetMapping("/Bird-cage Product/{productID}/edit")
//    public String editBirdCage(@PathVariable("productID")long productID, Model model){
//        Bird_CageDTO birdCage=productService.findProductById(productID);
//        model.addAttribute("birdCage",birdCage);
//        return "Bird-cage-edit";
//    }
    @PostMapping("/products/{productID}/edit")
    public String updateProduct(@PathVariable("productID")long productId, @ModelAttribute("product") ProductDTO product){
        product.setProductId(productId);
        productService.updateProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/admin/product-table/bird-cage/add")
    @RolesAllowed({"ADMIN"})
    public String addBirdCage(){
        return "bird-cage";
    }


}

