package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.dto.BirdCageDTO;
import com.group3.torikago.Torikago.Shop.dto.CustomizedBirdCageDTO;
import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.CustomizedBirdCage;
import com.group3.torikago.Torikago.Shop.service.BirdCageService;
import com.group3.torikago.Torikago.Shop.service.CustomizedBirdCageService;
import com.group3.torikago.Torikago.Shop.service.impl.ProductImplement;
import com.group3.torikago.Torikago.Shop.util.CloudinaryUpload;
import com.group3.torikago.Torikago.Shop.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class CustomizedProductController {
    private CustomizedBirdCageService customizedBirdCageService;

    private BirdCageService birdCageService;

    private CloudinaryUpload cloudinaryUpload;
    public CustomizedProductController (CustomizedBirdCageService customizedBirdCageService, BirdCageService birdCageService, CloudinaryUpload cloudinaryUpload){
        this.customizedBirdCageService = customizedBirdCageService;
        this.birdCageService = birdCageService;
        this.cloudinaryUpload = cloudinaryUpload;
    }

    @GetMapping("/customized-bird-cage/{productId}/edit")
    public String editCustomizedBirdCage(@PathVariable("productId") Long productId, Model model) {
        BirdCageDetail birdCageDetail = birdCageService.findBirdCageByID(productId);
        model.addAttribute("birdCageDetail", birdCageDetail);
        model.addAttribute("birdCage", birdCageDetail.getBirdCage());
        return "customized-bird-cage-edit";
    }

    @PostMapping("/customized-bird-cage/{productId}/edit")
    public String saveBirdCage(@PathVariable("productId") Long productId,
                               @ModelAttribute("birdCageDetail") @Valid CustomizedBirdCageDTO customizedBirdCageDTO,
                               BindingResult birdCageBindingResult,
                               @ModelAttribute("birdCage") @Valid ProductDTO productDTO,
                               BindingResult productBindingResult, Model model) throws IOException {
        if (birdCageBindingResult.hasErrors() || productBindingResult.hasErrors()) {
            return "customized-bird-cage-edit";
        }


        productDTO.setProductType("Customized");
        productDTO.setUnitsOnOrder(0);
        productDTO.setStatus(true);

        BirdCageDetail thisProduct = birdCageService.findBirdCageByID(productId);

        if(thisProduct.getDimension().equals(customizedBirdCageDTO.getDimension()) &&
            thisProduct.getCageShape().equals(customizedBirdCageDTO.getCageShape()) &&
                thisProduct.getBarSpacing() == customizedBirdCageDTO.getBarSpacing() &&
                thisProduct.getBirdWingSpan() == customizedBirdCageDTO.getBirdWingSpan()){
            BirdCageDetail birdCageDetail = birdCageService.findBirdCageByID(productId);
            model.addAttribute("birdCageDetail", birdCageDetail);
            model.addAttribute("birdCage", birdCageDetail.getBirdCage());
                model.addAttribute("ERROR", "Nothing changed");
            return "customized-bird-cage-edit";
        }

        productDTO.setMainImage(thisProduct.getBirdCage().getMainImage());
        productDTO.setExtraImage1(thisProduct.getBirdCage().getExtraImage1());
        productDTO.setExtraImage2(thisProduct.getBirdCage().getExtraImage2());
        productDTO.setExtraImage3(thisProduct.getBirdCage().getExtraImage3());
//        String imageURLMain = cloudinaryUpload.uploadFile(fileUploadMain);
//        productDTO.setMainImage(imageURLMain);
//        String imageURLExtra1 = cloudinaryUpload.uploadFile(fileUploadExtra1);
//        productDTO.setExtraImage1(imageURLExtra1);
//        String imageURLExtra2 = cloudinaryUpload.uploadFile(fileUploadExtra2);
//        productDTO.setExtraImage2(imageURLExtra2);
//        String imageURLExtra3 = cloudinaryUpload.uploadFile(fileUploadExtra3);
//        productDTO.setExtraImage3(imageURLExtra3);

        customizedBirdCageDTO.setStatus("Pending");
        CustomizedBirdCage savedProduct = customizedBirdCageService.saveCustomizedBirdCage(customizedBirdCageDTO, productDTO);

//        FileUploadUtil.copyFile("./product-images/" + productId, "./product-images/" + savedProduct.getCustomizedBirdCage().getId(), productDTO.getMainImage());
//        FileUploadUtil.copyFile("./product-images/" + productId, "./product-images/" + savedProduct.getCustomizedBirdCage().getId(), productDTO.getExtraImage1());
//        FileUploadUtil.copyFile("./product-images/" + productId, "./product-images/" + savedProduct.getCustomizedBirdCage().getId(), productDTO.getExtraImage2());
//        FileUploadUtil.copyFile("./product-images/" + productId, "./product-images/" + savedProduct.getCustomizedBirdCage().getId(), productDTO.getExtraImage3());
//        String uploadDir = "./product-images/" + savedProduct.getCustomizedBirdCage().getId();

        return "redirect:/";
    }
//    @GetMapping("/customized-bird-ca  ge/{ID}")
//    public String sendRequest(@PathVariable("ID") Long id, Model model){
//        CustomizedBirdCage customizedBirdCage = customizedBirdCageService.findByCustomizedBirdCage_Id(id);
//        model.addAttribute("product", customizedBirdCage.getCustomizedBirdCage());
//        model.addAttribute("customizedBirdCage", customizedBirdCage);
//        return "customized-bird-cage-edit";
//    }
//
//    @PostMapping("/customized-bird-cage/{ID}")
//    public String acceptRequest(@PathVariable("ID") Long id, @RequestParam("extra-image") MultipartFile[] extraMultipartFiles,
//                                @ModelAttribute("accessoryDetail") @Valid CustomizedBirdCageDTO customizedBirdCageDTO, BindingResult result,
//                                @ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult customizedBirdCageBindingResult,
//                                BindingResult productBindingResult) throws IOException {
//        if (customizedBirdCageBindingResult.hasErrors() || productBindingResult.hasErrors()) {
//            // If there are validation errors, return to the form page with errors
//            return "customized-bird-cage-edit";
//        }
//        CustomizedBirdCage customizedBirdCage = customizedBirdCageService.findByCustomizedBirdCage_Id(id);
//        int count = 0;
//        for (MultipartFile extraMultipart : extraMultipartFiles) {
//            String extraImageName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
//            if (count == 0) {
//                if (extraImageName.isEmpty()) productDTO.setMainImage(customizedBirdCage.getCustomizedBirdCage().getMainImage());
//                else productDTO.setMainImage(extraImageName);
//            }
//            if (count == 1) {
//                if (extraImageName.isEmpty()) productDTO.setExtraImage1(customizedBirdCage.getCustomizedBirdCage().getExtraImage1());
//                else productDTO.setExtraImage1(extraImageName);
//            }
//            if (count == 2) {
//                if (extraImageName.isEmpty()) productDTO.setExtraImage2(customizedBirdCage.getCustomizedBirdCage().getExtraImage2());
//                else productDTO.setExtraImage2(extraImageName);
//            }
//            if (count == 3) {
//                if (extraImageName.isEmpty()) productDTO.setExtraImage3(customizedBirdCage.getCustomizedBirdCage().getExtraImage3());
//                else productDTO.setExtraImage3(extraImageName);
//            }
//
//            count++;
//        }
//        customizedBirdCageDTO.setId(id);
//        productDTO.setId(customizedBirdCage.getCustomizedBirdCage().getId());
//        productDTO.setProductType(customizedBirdCage.getCustomizedBirdCage().getProductType());
//        productDTO.setUnitsOnOrder(customizedBirdCage.getCustomizedBirdCage().getUnitsOnOrder());
//        ProductImplement productImplement = new ProductImplement();
////        customizedBirdCageDTO.setCustomizedBirdCage(productImplement.mapToProduct(productDTO));
////        customizedBirdCageService.updateCustomizedBirdCage(customizedBirdCageDTO);
//        String uploadDir = "./product-images/" + customizedBirdCage.getCustomizedBirdCage().getId();
//        for (MultipartFile extraMultipart : extraMultipartFiles) {
//            String fileName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
//            if (fileName.isEmpty()) continue;
//            FileUploadUtil.saveFile(uploadDir, extraMultipart, fileName);
//        }
//        return "redirect:/admin/product-table";
//    }
//
//    @PostMapping("/customized-bird-cage/{ID}")
//    public String updateCustomizedBirdCage(@PathVariable("ID") Long id, @RequestParam("extra-image") MultipartFile[] extraMultipartFiles,
//                                           @ModelAttribute("accessoryDetail") @Valid CustomizedBirdCageDTO customizedBirdCageDTO, BindingResult result,
//                                           @ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult customizedBirdCageBindingResult,
//                                           BindingResult productBindingResult) throws IOException {
//        if (customizedBirdCageBindingResult.hasErrors() || productBindingResult.hasErrors()) {
//            // If there are validation errors, return to the form page with errors
//            return "customized-bird-cage-edit";
//        }
//        CustomizedBirdCage customizedBirdCage = customizedBirdCageService.findByCustomizedBirdCage_Id(id);
//        int count = 0;
//        for (MultipartFile extraMultipart : extraMultipartFiles) {
//            String extraImageName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
//            if (count == 0) {
//                if (extraImageName.isEmpty()) productDTO.setMainImage(customizedBirdCage.getCustomizedBirdCage().getMainImage());
//                else productDTO.setMainImage(extraImageName);
//            }
//            if (count == 1) {
//                if (extraImageName.isEmpty()) productDTO.setExtraImage1(customizedBirdCage.getCustomizedBirdCage().getExtraImage1());
//                else productDTO.setExtraImage1(extraImageName);
//            }
//            if (count == 2) {
//                if (extraImageName.isEmpty()) productDTO.setExtraImage2(customizedBirdCage.getCustomizedBirdCage().getExtraImage2());
//                else productDTO.setExtraImage2(extraImageName);
//            }
//            if (count == 3) {
//                if (extraImageName.isEmpty()) productDTO.setExtraImage3(customizedBirdCage.getCustomizedBirdCage().getExtraImage3());
//                else productDTO.setExtraImage3(extraImageName);
//            }
//
//            count++;
//        }
//        customizedBirdCageDTO.setId(id);
//        productDTO.setId(customizedBirdCage.getCustomizedBirdCage().getId());
//        productDTO.setProductType(customizedBirdCage.getCustomizedBirdCage().getProductType());
//        productDTO.setUnitsOnOrder(customizedBirdCage.getCustomizedBirdCage().getUnitsOnOrder());
//        ProductImplement productImplement = new ProductImplement();
//        customizedBirdCageDTO.setCustomizedBirdCage(productImplement.mapToProduct(productDTO));
//        customizedBirdCageService.updateCustomizedBirdCage(customizedBirdCageDTO);
//        String uploadDir = "./product-images/" + customizedBirdCage.getCustomizedBirdCage().getId();
//        for (MultipartFile extraMultipart : extraMultipartFiles) {
//            String fileName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
//            if (fileName.isEmpty()) continue;
//            FileUploadUtil.saveFile(uploadDir, extraMultipart, fileName);
//        }
//        return "redirect:/admin/product-table";
//    }
}
