package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.dto.AccessoryDTO;
import com.group3.torikago.Torikago.Shop.dto.CustomizedBirdCageDTO;
import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.AccessoryDetail;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.CustomizedBirdCage;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.service.CustomizedBirdCageService;
import com.group3.torikago.Torikago.Shop.service.ProductService;
import com.group3.torikago.Torikago.Shop.util.FileUploadUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ManagerController {

    private ProductService productService;

    private CustomizedBirdCageService customizedBirdCageService;
    public ManagerController(ProductService productService, CustomizedBirdCageService customizedBirdCageService){
        this.customizedBirdCageService = customizedBirdCageService;
        this.productService = productService;
    }
    @GetMapping("/manager")
    @RolesAllowed({"MANAGER"})
    public String managerPage() {
        return "manager-dashboard";
    }

    @GetMapping("/manager/custom-orders")
    @RolesAllowed({"MANAGER"})
    public String getListCustomizedProduct(Model model,
                                 @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                 @RequestParam(name = "pageSize", defaultValue = "8") int pageSize,
                                 @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                                 @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                                 @Param("keyword") String keyword) {
        // Use these parameters to fetch a paginated and sorted list of products
        Page<Product> productsPage = productService.findCustomizedProducts(pageNumber, pageSize, sortField, sortDir,keyword);

        model.addAttribute("productsPage", productsPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword",keyword);
        return "manager-custom-orders";
    }

    @GetMapping("/manager/custom-orders/{productId}/edit")
    @RolesAllowed({"MANAGER"})
    public String editProduct(Model model, @PathVariable("productId") Long productId) {
        CustomizedBirdCage customizedBirdCage = customizedBirdCageService.findByCustomizedBirdCage_Id(productId);
        model.addAttribute("birdCageDetail", customizedBirdCage);
        model.addAttribute("birdCage", customizedBirdCage.getCustomizedBirdCage());
        return "manager-custom-orders-edit";
    }

    @PostMapping("/manager/custom-orders/{productId}/edit")
    public String updateCustomizedProduct(@ModelAttribute("birdCageDetail") @Valid CustomizedBirdCageDTO customizedBirdCageDTO,
                                  BindingResult customizedProductBindingResult,
                                  @ModelAttribute("birdCage") @Valid ProductDTO productDTO,
                                  BindingResult productBindingResult,
                                  @PathVariable("productId") Long productId) throws IOException {
//        if (customizedProductBindingResult.hasErrors() || productBindingResult.hasErrors()) {
//            return "manager-custom-orders-edit";
//        }

        CustomizedBirdCage customizedBirdCage = customizedBirdCageService.findByCustomizedBirdCage_Id(productId);
        customizedBirdCage.setStatus(customizedBirdCageDTO.getStatus());
        if (customizedBirdCageDTO.getStatus().equals("Accepted")){
            // send notice
        }
        else{
            //send notice
        }
//        customizedBirdCageDTO.setId(customizedBirdCage.getId());
//        productDTO.setId(productId);
//        productDTO.setUnitsOnOrder(customizedBirdCage.getCustomizedBirdCage().getUnitsOnOrder());
//        customizedBirdCage.updateAccessory(accessoryDTO, productDTO);
//
//        String uploadDir = "./product-images/" + accessory.getAccessory().getId();
//        for (MultipartFile extraMultipart : extraMultipartFiles) {
//            String fileName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
//            if (fileName.isEmpty()) continue;
//            FileUploadUtil.saveFile(uploadDir, extraMultipart, fileName);
//        }
        customizedBirdCageService.updateCustomizedBirdCage(customizedBirdCage);
        return "redirect:/manager/custom-orders";
    }
}
