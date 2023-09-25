package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.dto.BirdCageDTO;
import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.service.BirdCageService;
import com.group3.torikago.Torikago.Shop.service.ProductService;
import jakarta.annotation.security.RolesAllowed;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {
    @GetMapping("/admin")
    @RolesAllowed({"ADMIN"})
    public String adminPage() {
        return "admin-dashboard";
    }

    private ProductService productService;

    private BirdCageService birdCageService;

    @Autowired
    public AdminController(ProductService productService, BirdCageService birdCageService) {
        this.productService = productService;
        this.birdCageService = birdCageService;
    }

    @GetMapping("/admin/product-table")
    @RolesAllowed({"ADMIN"})
    public String getListProduct(Model model) {
        List<ProductDTO> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "admin-product";
    }

    //    @GetMapping("/admin/product-table/bird-cage/add")
//    @RolesAllowed({"ADMIN"})
//    public String addProduct(Model model){
//        Product product=new Product();
//        model.addAttribute("product",product);
//        return "bird-cage";
//    }
//    @PostMapping("/admin/product-table/bird-cage/add")
//    public String saveProduct(@ModelAttribute("product") Product product){
//        productService.saveProduct(product);
//        return "redirect:/admin";
//    }
    @GetMapping("/admin/product-table/bird-cage/add")
    @RolesAllowed({"ADMIN"})
    public String addBirdCage(Model model) {
        BirdCageDetail birdCageDetail = new BirdCageDetail();
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("birdCageDetail", birdCageDetail);
        return "bird-cage";
    }

    @PostMapping("/admin/product-table/bird-cage/add")
    public String saveBirdCage(@ModelAttribute("birdCageDetail") @Valid BirdCageDTO birdCageDTO, BindingResult result,
                               @ModelAttribute("product") @Valid ProductDTO productDTO,BindingResult birdCageBindingResult,
                               BindingResult productBindingResult) {
        if (birdCageBindingResult.hasErrors() == productBindingResult.hasErrors()) {
            // If there are validation errors, return to the form page with errors
            return "bird-cage";
        }
        productDTO.setProductType("bird cage");
        productDTO.setUnitsOnOrder(0);
        birdCageService.saveBirdCage(birdCageDTO, productDTO);
        return "redirect:/admin";
    }
//    @GetMapping("/admin/product-table/{productID}/edit")
//    public String editProduct(@PathVariable("productID")Long productId, Model model){
//       ProductDTO birdCage=productService.findProductById(productId);
//        model.addAttribute("birdCage",birdCage);
//      return "Bird-cage";
//   }
//    @PostMapping("/admin/product-table/{productID}/edit")
//    public String updateProduct(@PathVariable("productID")Long productId, @ModelAttribute("product") ProductDTO product){
//        product.setProductId(productId);
//        productService.updateProduct(product);
//        return "redirect:/admin";
//    }
//    @PostMapping("/admin/product-table/{productID}/edit")
//    public String updateBirdCage(@PathVariable("productID")Product birdCageId, @ModelAttribute("birdCage") BirdCageDTO birdCage){
//        birdCage.setBirdCage(birdCageId);
//        birdCageService.updateBirdCage(birdCage);
//        return "redirect:/admin";
//    }

}

