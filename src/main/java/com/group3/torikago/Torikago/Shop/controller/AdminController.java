package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.dto.AccessoryDTO;
import com.group3.torikago.Torikago.Shop.dto.BirdCageDTO;
import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.*;
import com.group3.torikago.Torikago.Shop.service.AccessoryService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(ProductService productService, UserService userService, BirdCageService birdCageService, AccessoryService accessoryService, PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.userService = userService;
        this.birdCageService = birdCageService;
        this.accessoryService = accessoryService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/product-table")
    @RolesAllowed({"ADMIN"})
    public String getListProduct(Model model,
                                 @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                 @RequestParam(name = "pageSize", defaultValue = "8") int pageSize,
                                 @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                                 @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                                 @Param("keyword") String keyword) {
        // Use these parameters to fetch a paginated and sorted list of products
        Page<ProductDTO> productsPage = productService.findPaginatedProducts(pageNumber, pageSize, sortField, sortDir,keyword);
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword",keyword);
        return "admin-product";
    }

    @GetMapping("/admin/product-table/bird-cage/add")
    @RolesAllowed({"ADMIN"})
    public String addBirdCage(Model model) {
        BirdCageDetail birdCageDetail = new BirdCageDetail();
        Product birdCage = new Product();
        model.addAttribute("birdCage", birdCage);
        model.addAttribute("birdCageDetail", birdCageDetail);
        return "bird-cage";
    }

    @PostMapping("/admin/product-table/bird-cage/add")
    public String saveBirdCage(@RequestParam("extra-image") MultipartFile[] extraMultipartFiles,
                               @ModelAttribute("birdCageDetail") @Valid BirdCageDTO birdCageDTO,
                               BindingResult birdCageBindingResult,
                               @ModelAttribute("birdCage") @Valid ProductDTO productDTO,
                               BindingResult productBindingResult) throws IOException {   
        if (birdCageBindingResult.hasErrors() || productBindingResult.hasErrors()) {
            return "bird-cage";
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
        
        productDTO.setProductType("Bird Cage");
        productDTO.setUnitsOnOrder(0);

        BirdCageDetail savedProduct = birdCageService.saveBirdCage(birdCageDTO, productDTO);

        String uploadDir = "./product-images/" + savedProduct.getBirdCage().getId();

        for (MultipartFile extraMultipart : extraMultipartFiles) {
            String fileName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
            if (fileName.isEmpty()) continue;
            FileUploadUtil.saveFile(uploadDir, extraMultipart, fileName);
        }
        return "redirect:/admin/product-table";
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
                                  @ModelAttribute("accessoryDetail") @Valid AccessoryDTO accessoryDTO, 
                                  BindingResult accessoryBindingResult,
                                  @ModelAttribute("product") @Valid ProductDTO productDTO, 
                                  BindingResult productBindingResult) throws IOException {
        if (accessoryBindingResult.hasErrors() || productBindingResult.hasErrors()) {
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

        productDTO.setProductType("Accessory");
        productDTO.setUnitsOnOrder(0);

        AccessoryDetail savedProduct = accessoryService.saveAccessory(accessoryDTO, productDTO);

        String uploadDir = "./product-images/" + savedProduct.getAccessory().getId();

        for (MultipartFile extraMultipart : extraMultipartFiles) {
            String fileName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
            if (fileName.isEmpty()) continue;
            FileUploadUtil.saveFile(uploadDir, extraMultipart, fileName);
        }
        return "redirect:/admin/product-table";
    }

    @GetMapping("/admin/product-table/{productType}/{productId}/edit")
    @RolesAllowed({"ADMIN"})
    public String editProduct(Model model, @PathVariable("productType") String productType,
                              @PathVariable("productId") Long productId) {
        if (productType.equalsIgnoreCase("Bird Cage")) {
            BirdCageDetail birdCageDetail = birdCageService.findBirdCageByID(productId);            
            model.addAttribute("birdCageDetail", birdCageDetail);
            model.addAttribute("birdCage", birdCageDetail.getBirdCage());
            return "bird-cage-edit";
        } else if (productType.equalsIgnoreCase("Accessory")) {
            AccessoryDetail accessoryDetail = accessoryService.findAccessoryById(productId);
            model.addAttribute("accessoryDetail", accessoryDetail);
            model.addAttribute("accessory", accessoryDetail.getAccessory());
            return "accessory-edit";
        }
        return "redirect:/admin/product-table";
    }

   @PostMapping("/admin/product-table/accessory/{productId}/edit")
    public String updateAccessory(@RequestParam("extra-image") MultipartFile[] extraMultipartFiles,
                                  @ModelAttribute("accessoryDetail") @Valid AccessoryDTO accessoryDTO, 
                                  BindingResult accessoryBindingResult,
                                  @ModelAttribute("accessory") @Valid ProductDTO productDTO, 
                                  BindingResult productBindingResult,
                                  @PathVariable("productId") Long productId) throws IOException {
        if (accessoryBindingResult.hasErrors() || productBindingResult.hasErrors()) {
            return "accessory-edit";
        }
        
        AccessoryDetail accessory = accessoryService.findAccessoryById(productId);
        int count = 0;
        for (MultipartFile extraMultipart : extraMultipartFiles) {
            String extraImageName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
            if (count == 0) {
                if (extraImageName.isEmpty()) productDTO.setMainImage(accessory.getAccessory().getMainImage());
                else productDTO.setMainImage(extraImageName);
            }
            if (count == 1) {
                if (extraImageName.isEmpty()) productDTO.setExtraImage1(accessory.getAccessory().getExtraImage1());
                else productDTO.setExtraImage1(extraImageName);
            }
            if (count == 2) {
                if (extraImageName.isEmpty()) productDTO.setExtraImage2(accessory.getAccessory().getExtraImage2());
                else productDTO.setExtraImage2(extraImageName);
            }
            if (count == 3) {
                if (extraImageName.isEmpty()) productDTO.setExtraImage3(accessory.getAccessory().getExtraImage3());
                else productDTO.setExtraImage3(extraImageName);
            }

            count++;
        }
        accessoryDTO.setId(accessory.getId());
        productDTO.setId(productId);
        productDTO.setProductType("Accessory");
        productDTO.setUnitsOnOrder(accessory.getAccessory().getUnitsOnOrder());
        accessoryService.updateAccessory(accessoryDTO, productDTO);
        
        String uploadDir = "./product-images/" + accessory.getAccessory().getId();
        for (MultipartFile extraMultipart : extraMultipartFiles) {
            String fileName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
            if (fileName.isEmpty()) continue;
            FileUploadUtil.saveFile(uploadDir, extraMultipart, fileName);
        }
        return "redirect:/admin/product-table";
    }

    @PostMapping("/admin/product-table/bird-cage/{productId}/edit")
    public String updateBirdCage(@RequestParam("extra-image") MultipartFile[] extraMultipartFiles,
                               @ModelAttribute("birdCageDetail") @Valid BirdCageDTO birdCageDTO,
                               BindingResult birdCageBindingResult,
                               @ModelAttribute("birdCage") @Valid ProductDTO productDTO,
                               BindingResult productBindingResult, 
                               @PathVariable("productId") Long productId) throws IOException {
        if (birdCageBindingResult.hasErrors() || productBindingResult.hasErrors()) {
            return "bird-cage-edit";
        }
        
        BirdCageDetail birdCageDetail = birdCageService.findBirdCageByID(productId);
        int count = 0;
        for (MultipartFile extraMultipart : extraMultipartFiles) {
            String extraImageName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
            if (count == 0) {
                if (extraImageName.isEmpty()) productDTO.setMainImage(birdCageDetail.getBirdCage().getMainImage());
                else productDTO.setMainImage(extraImageName);
            }
            if (count == 1) {
                if (extraImageName.isEmpty()) productDTO.setExtraImage1(birdCageDetail.getBirdCage().getExtraImage1());
                else productDTO.setExtraImage1(extraImageName);
            }
            if (count == 2) {
                if (extraImageName.isEmpty()) productDTO.setExtraImage2(birdCageDetail.getBirdCage().getExtraImage2());
                else productDTO.setExtraImage2(extraImageName);
            }
            if (count == 3) {
                if (extraImageName.isEmpty()) productDTO.setExtraImage3(birdCageDetail.getBirdCage().getExtraImage3());
                else productDTO.setExtraImage3(extraImageName);
            }
            count++;
        }

        birdCageDTO.setId(birdCageDetail.getId());
        productDTO.setId(productId);
        productDTO.setProductType("Bird Cage");
        productDTO.setUnitsOnOrder(birdCageDetail.getBirdCage().getUnitsOnOrder());
        birdCageService.updateBirdCage(birdCageDTO, productDTO);

        String uploadDir = "./product-images/" + birdCageDetail.getBirdCage().getId();
//        FileUploadUtil.resetDirectory(uploadDir);

        for (MultipartFile extraMultipart : extraMultipartFiles) {
            String fileName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());
            if (fileName.isEmpty()) continue;
            FileUploadUtil.saveFile(uploadDir, extraMultipart, fileName);
        }
        return "redirect:/admin/product-table";
    }

    @GetMapping("/admin/users-table")
    @RolesAllowed({"ADMIN"})
    public String getListUsers(Model model, @Param("keyword") String keyword) {
        List<User> users = userService.listAllUsers(keyword);
        model.addAttribute("users", users);
        model.addAttribute("keyword",keyword);
        return "admin-users";
    }



    @GetMapping("/users/edit/{id}")
    public String editUserAdmin(@PathVariable("id") Long id, Model model) {
        User user = userService.get(id);
        List<Role> listRoles = userService.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "admin-user-edit";
    }
    @PostMapping("/users/save")
    public String saveUser(User user) {

        userService.save(user);

        return "redirect:/admin/users-table";
    }
}

