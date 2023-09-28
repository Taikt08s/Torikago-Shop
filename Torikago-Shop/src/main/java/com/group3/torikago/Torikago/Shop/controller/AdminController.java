package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.dto.BirdCageDTO;
import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.service.BirdCageService;
import com.group3.torikago.Torikago.Shop.service.ProductService;
import com.group3.torikago.Torikago.Shop.util.FileUploadUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public String saveBirdCage(@RequestParam("primary-image") MultipartFile mainMultipartFile,
                               @RequestParam("extra-image") MultipartFile[] extraMultipartFiles,
                               @ModelAttribute("birdCageDetail") @Valid BirdCageDTO birdCageDTO,
                               BindingResult birdCageBindingResult,
                               @ModelAttribute("product") @Valid ProductDTO productDTO,
                               BindingResult productBindingResult) throws IOException {

        String mainImageName = StringUtils.cleanPath(mainMultipartFile.getOriginalFilename());
        productDTO.setMainImage(mainImageName);
        int count = 0;
        for (MultipartFile extraMultipart : extraMultipartFiles) {
            String extraImageName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
            if (count == 0) productDTO.setExtraImage1(extraImageName);
            if (count == 1) productDTO.setExtraImage2(extraImageName);
            if (count == 2) productDTO.setExtraImage3(extraImageName);
            count++;
        }

        if (birdCageBindingResult.hasErrors() || productBindingResult.hasErrors()) {
            // If there are validation errors, return to the form page with errors
            return "bird-cage";
        }
        productDTO.setProductType("Bird Cage");
        productDTO.setUnitsOnOrder(0);

        BirdCageDetail savedProduct = birdCageService.saveBirdCage(birdCageDTO, productDTO);

        String uploadDir = "./product-images/" + savedProduct.getId();

        for (MultipartFile extraMultipart : extraMultipartFiles) {
            String fileName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
            FileUploadUtil.saveFile(uploadDir, extraMultipart, fileName);
        }
        return "redirect:/admin";
    }

}

