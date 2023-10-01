package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.dto.AccessoryDTO;
import com.group3.torikago.Torikago.Shop.dto.BirdCageDTO;
import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.AccessoryDetail;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.service.AccessoryService;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.service.BirdCageService;
import com.group3.torikago.Torikago.Shop.service.ProductService;
import com.group3.torikago.Torikago.Shop.service.UserService;
import com.group3.torikago.Torikago.Shop.service.impl.ProductImplement;
import com.group3.torikago.Torikago.Shop.util.FileUploadUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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

import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {
    @GetMapping("/admin")
    @RolesAllowed({"ADMIN"})
    public String adminPage() {
        return "admin-dashboard";
    }

    private ProductService productService;
    private UserService userService;
    private BirdCageService birdCageService;
    private AccessoryService accessoryService;

    @Autowired
    public AdminController(ProductService productService, UserService userService, BirdCageService birdCageService,AccessoryService accessoryService) {
        this.productService = productService;
        this.userService = userService;
        this.birdCageService = birdCageService;
        this.accessoryService = accessoryService;
    }

    @GetMapping("/admin/product-table")
    @RolesAllowed({"ADMIN"})
    public String getListProduct(Model model,
                                 @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                 @RequestParam(name = "pageSize", defaultValue = "8") int pageSize,
                                 @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                                 @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {
        // Use these parameters to fetch a paginated and sorted list of products
        Page<ProductDTO> productsPage = productService.findPaginatedProducts(pageNumber, pageSize, sortField, sortDir);
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
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
    public String saveBirdCage(@RequestParam("extra-image") MultipartFile[] extraMultipartFiles,
                               @ModelAttribute("birdCageDetail") @Valid BirdCageDTO birdCageDTO,
                               BindingResult birdCageBindingResult,
                               @ModelAttribute("product") @Valid ProductDTO productDTO,
                               BindingResult productBindingResult) throws IOException {

//        String mainImageName = StringUtils.cleanPath(mainMultipartFile.getOriginalFilename());
//        productDTO.setMainImage(mainImageName);
        int count = 0;
        for (MultipartFile extraMultipart : extraMultipartFiles) {
            String extraImageName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
            if (count == 0) productDTO.setMainImage(extraImageName);
            if (count == 1) productDTO.setExtraImage1(extraImageName);
            if (count == 2) productDTO.setExtraImage2(extraImageName);
            if (count == 3) productDTO.setExtraImage3(extraImageName);
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
    @GetMapping("/admin/product-table/accessory/add")
    @RolesAllowed({"ADMIN"})
    public String addAccessory(Model model) {
        AccessoryDetail accessoryDetail = new AccessoryDetail();
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("accessoryDetail", accessoryDetail);
        return "accessory";
    }

    @PostMapping("/admin/product-table/accessory/add")
    public String saveAccessory(@RequestParam("extra-image") MultipartFile[] extraMultipartFiles,
                                @ModelAttribute("accessoryDetail") @Valid AccessoryDTO accessoryDTO, BindingResult result,
                                @ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult accessoryBindingResult,
                                BindingResult productBindingResult) throws IOException{
        if (accessoryBindingResult.hasErrors() || productBindingResult.hasErrors()) {
            // If there are validation errors, return to the form page with errors
            return "accessory";
        }
        int count = 0;
        for (MultipartFile extraMultipart : extraMultipartFiles) {
            String extraImageName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
            if (count == 0) productDTO.setMainImage(extraImageName);
            if (count == 1) productDTO.setExtraImage1(extraImageName);
            if (count == 2) productDTO.setExtraImage2(extraImageName);
            if (count == 3) productDTO.setExtraImage3(extraImageName);
            count++;
        }


        productDTO.setProductType("accessory");
        productDTO.setUnitsOnOrder(0);
        AccessoryDetail savedProduct =  accessoryService.saveAccessory(accessoryDTO, productDTO);

        String uploadDir = "./product-images/" + savedProduct.getAccessory().getId();

        for (MultipartFile extraMultipart : extraMultipartFiles) {
            String fileName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
            FileUploadUtil.saveFile(uploadDir, extraMultipart, fileName);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/product-table/accessory-edit/{ID}/edit")
    @RolesAllowed({"ADMIN"})
    public String editAccessory(@PathVariable("ID") Long id, Model model) {
        AccessoryDTO accessoryDTO = accessoryService.findAccessoryById(id);
        model.addAttribute("product", accessoryDTO.getAccessory());
        model.addAttribute("accessoryDetail", accessoryDTO);
        return "accessory-edit";
    }

    @PostMapping("/admin/product-table/accessory-edit/{ID}/edit")
    public String updateAccessory(@PathVariable("ID") Long id, @RequestParam("extra-image") MultipartFile[] extraMultipartFiles,
                                  @ModelAttribute("accessoryDetail") @Valid AccessoryDTO accessoryDTO, BindingResult result,
                                  @ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult accessoryBindingResult,
                                  BindingResult productBindingResult) throws IOException {
        if (accessoryBindingResult.hasErrors() || productBindingResult.hasErrors()) {
            // If there are validation errors, return to the form page with errors
            return "accessory-edit";
        }
        int count = 0;
        for (MultipartFile extraMultipart : extraMultipartFiles) {
            String extraImageName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
            if (count == 0) productDTO.setMainImage(extraImageName);
            if (count == 1) productDTO.setExtraImage1(extraImageName);
            if (count == 2) productDTO.setExtraImage2(extraImageName);
            if (count == 3) productDTO.setExtraImage3(extraImageName);
            count++;
        }

        AccessoryDTO accessory = accessoryService.findAccessoryById(id);
        accessoryDTO.setId(id);
        productDTO.setId(accessory.getAccessory().getId());
        productDTO.setProductType(accessory.getAccessory().getProductType());
        productDTO.setUnitsOnOrder(accessory.getAccessory().getUnitsOnOrder());
        ProductImplement productImplement = new ProductImplement();
        accessoryDTO.setAccessory(productImplement.mapToProduct(productDTO));
        accessoryService.updateAccessory(accessoryDTO);
        String uploadDir = "./product-images/" + accessory.getAccessory().getId();

        for (MultipartFile extraMultipart : extraMultipartFiles) {
            String fileName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
            FileUploadUtil.saveFile(uploadDir, extraMultipart, fileName);
        }
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

    @GetMapping("/admin/product-table/{productId}/delete")
    @RolesAllowed({"ADMIN"})
    public String deleteBirdCage(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/admin/product-table";
    }

    @GetMapping("/admin/users-table")
    @RolesAllowed({"ADMIN"})
    public String getListUsers(Model model) {
        List<User> users = userService.listAllUsers();
        model.addAttribute("users", users);
        return "admin-users";
    }
}

