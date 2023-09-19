package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.DTO.Bird_CageDTO;
import com.group3.torikago.Torikago.Shop.model.Bird_Cage;
import com.group3.torikago.Torikago.Shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Controller
public class ProductController {
    private ProductService productService;
@Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/products")
    public String listBirdCage(Model model){
        List<Bird_CageDTO> birdCages=productService.findAllProducts();
        model.addAttribute("products",birdCages);
        return "products-list";
    }
    @GetMapping("/products/new")
    public String createBirdcage(Model model){
        Bird_Cage birdCage=new Bird_Cage();
        model.addAttribute("product",birdCage);
        return "products-create";
    }
    @PostMapping("/products/new")
    public String saveBirdcage(@ModelAttribute("product") Bird_Cage birdCage){
        productService.saveBirdCage(birdCage);
        return "redirect:/product";
    }


    //    @GetMapping("/Bird-cage Product/{productID}/edit")
//    public String editBirdCage(@PathVariable("productID")long productID, Model model){
//        Bird_CageDTO birdCage=productService.findProductById(productID);
//        model.addAttribute("birdCage",birdCage);
//        return "Bird-cage-edit";
//    }
    @PostMapping("/products/{productID}/edit")
    public String updateProduct(@PathVariable("productID")long productID, @ModelAttribute("birdCage") Bird_CageDTO birdCage){
        birdCage.setProductID(productID);
        productService.updateProduct(birdCage);
        return "redirect:/product";
    }
}
