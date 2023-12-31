package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.dto.AccessoryDTO;
import com.group3.torikago.Torikago.Shop.dto.BirdCageDTO;
import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.*;
import com.group3.torikago.Torikago.Shop.service.*;
import com.group3.torikago.Torikago.Shop.util.CloudinaryUpload;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class AdminController {
    private DashBoardService dashBoardService;
    private ProductService productService;
    private UserService userService;
    private BirdCageService birdCageService;
    private AccessoryService accessoryService;
    private PasswordEncoder passwordEncoder;

    private CloudinaryUpload cloudinaryUpload;

    @Autowired
    public AdminController(ProductService productService, UserService userService, BirdCageService birdCageService, AccessoryService accessoryService, PasswordEncoder passwordEncoder, CloudinaryUpload cloudinaryUpload,DashBoardService dashBoardService) {
        this.productService = productService;
        this.userService = userService;
        this.birdCageService = birdCageService;
        this.accessoryService = accessoryService;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryUpload = cloudinaryUpload;
        this.dashBoardService = dashBoardService;
    }

    @GetMapping("/admin")
    @RolesAllowed({"ADMIN"})
    public String adminPage(Model model) {
        double revenue = dashBoardService.Revenue();
        Product bestSeller = dashBoardService.BestSeller();
        int numberOfBestSeller = dashBoardService.NumberOfBestSeller();
        int newUsers = dashBoardService.NewUsers();
        int totalOrders = dashBoardService.TotalOrders();
        model.addAttribute("Revenue", revenue);
        model.addAttribute("NumberOfBestSeller", numberOfBestSeller);
        model.addAttribute("BestSeller", bestSeller);
        model.addAttribute("NewUsers", newUsers);
        model.addAttribute("TotalOrders", totalOrders);

        return "admin-dashboard";
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
        Page<ProductDTO> productsPage = productService.findPaginatedProducts(pageNumber, pageSize, sortField, sortDir, keyword);
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
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
    public String saveBirdCage(@RequestParam("main-image") MultipartFile fileUploadMain,
                               @RequestParam(value = "extra-image1", required = false) MultipartFile fileUploadExtra1,
                               @RequestParam(value = "extra-image2", required = false) MultipartFile fileUploadExtra2,
                               @RequestParam(value = "extra-image3", required = false) MultipartFile fileUploadExtra3,
                               @ModelAttribute("birdCageDetail") @Valid BirdCageDTO birdCageDTO,
                               BindingResult birdCageBindingResult,
                               @ModelAttribute("birdCage") @Valid ProductDTO productDTO,
                               BindingResult productBindingResult) throws IOException {

        if (birdCageBindingResult.hasErrors() || productBindingResult.hasErrors()) {
            return "bird-cage";
        }

        String imageURLMain = cloudinaryUpload.uploadFile(fileUploadMain);
        productDTO.setMainImage(imageURLMain);

        if (fileUploadExtra1 != null && !fileUploadExtra1.isEmpty()) {
            String imageURLExtra1 = cloudinaryUpload.uploadFile(fileUploadExtra1);
            productDTO.setExtraImage1(imageURLExtra1);
        }

        if (fileUploadExtra2 != null && !fileUploadExtra2.isEmpty()) {
            String imageURLExtra2 = cloudinaryUpload.uploadFile(fileUploadExtra2);
            productDTO.setExtraImage2(imageURLExtra2);
        }

        if (fileUploadExtra3 != null && !fileUploadExtra3.isEmpty()) {
            String imageURLExtra3 = cloudinaryUpload.uploadFile(fileUploadExtra3);
            productDTO.setExtraImage3(imageURLExtra3);
        }

        productDTO.setProductType("Bird Cage");
        productDTO.setUnitsOnOrder(0);

        birdCageService.saveBirdCage(birdCageDTO, productDTO);
        return "redirect:/admin/product-table?success";
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
    public String saveAccessory(@RequestParam("main-image") MultipartFile fileUploadMain,
                                @RequestParam(value = "extra-image1", required = false) MultipartFile fileUploadExtra1,
                                @RequestParam(value = "extra-image2", required = false) MultipartFile fileUploadExtra2,
                                @RequestParam(value = "extra-image3", required = false) MultipartFile fileUploadExtra3,
                                @ModelAttribute("accessoryDetail") @Valid AccessoryDTO accessoryDTO,
                                BindingResult accessoryBindingResult,
                                @ModelAttribute("product") @Valid ProductDTO productDTO,
                                BindingResult productBindingResult) throws IOException {
        if (accessoryBindingResult.hasErrors() || productBindingResult.hasErrors()) {
            return "accessory";
        }
        String imageURLMain = cloudinaryUpload.uploadFile(fileUploadMain);
        productDTO.setMainImage(imageURLMain);

        if (fileUploadExtra1 != null && !fileUploadExtra1.isEmpty()) {
            String imageURLExtra1 = cloudinaryUpload.uploadFile(fileUploadExtra1);
            productDTO.setExtraImage1(imageURLExtra1);
        }

        if (fileUploadExtra2 != null && !fileUploadExtra2.isEmpty()) {
            String imageURLExtra2 = cloudinaryUpload.uploadFile(fileUploadExtra2);
            productDTO.setExtraImage2(imageURLExtra2);
        }

        if (fileUploadExtra3 != null && !fileUploadExtra3.isEmpty()) {
            String imageURLExtra3 = cloudinaryUpload.uploadFile(fileUploadExtra3);
            productDTO.setExtraImage3(imageURLExtra3);
        }


        productDTO.setProductType("Accessory");
        productDTO.setUnitsOnOrder(0);

        accessoryService.saveAccessory(accessoryDTO, productDTO);


        return "redirect:/admin/product-table?success";
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
    public String updateAccessory(@RequestParam("main-image") MultipartFile fileUploadMain,
                                @RequestParam(value = "extra-image1", required = false) MultipartFile fileUploadExtra1,
                                @RequestParam(value = "extra-image2", required = false) MultipartFile fileUploadExtra2,
                                @RequestParam(value = "extra-image3", required = false) MultipartFile fileUploadExtra3,
                                  @ModelAttribute("accessoryDetail") @Valid AccessoryDTO accessoryDTO,
                                  BindingResult accessoryBindingResult,
                                  @ModelAttribute("accessory") @Valid ProductDTO productDTO,
                                  BindingResult productBindingResult,
                                  @PathVariable("productId") Long productId) throws IOException {       
        AccessoryDetail accessory = accessoryService.findAccessoryById(productId);
        
        if (fileUploadMain != null && !fileUploadMain.isEmpty()) {
            String imageURLMain = cloudinaryUpload.uploadFile(fileUploadMain);
            productDTO.setMainImage(imageURLMain);
        } else productDTO.setMainImage(accessory.getAccessory().getMainImage());

        if (accessoryBindingResult.hasErrors() || productBindingResult.hasErrors()) {
            return "accessory-edit";
        }
        
        if (fileUploadExtra1 != null && !fileUploadExtra1.isEmpty()) {
            String imageURLExtra1 = cloudinaryUpload.uploadFile(fileUploadExtra1);
            productDTO.setExtraImage1(imageURLExtra1);
        }else productDTO.setExtraImage1(accessory.getAccessory().getExtraImage1());

        if (fileUploadExtra2 != null && !fileUploadExtra2.isEmpty()) {
            String imageURLExtra2 = cloudinaryUpload.uploadFile(fileUploadExtra2);
            productDTO.setExtraImage2(imageURLExtra2);
        }else productDTO.setExtraImage2(accessory.getAccessory().getExtraImage2());

        if (fileUploadExtra3 != null && !fileUploadExtra3.isEmpty()) {
            String imageURLExtra3 = cloudinaryUpload.uploadFile(fileUploadExtra3);
            productDTO.setExtraImage3(imageURLExtra3);
        }else productDTO.setExtraImage3(accessory.getAccessory().getExtraImage3());

        accessoryDTO.setId(accessory.getId());
        productDTO.setId(productId);
        productDTO.setProductType("Accessory");
        productDTO.setUnitsOnOrder(accessory.getAccessory().getUnitsOnOrder());
        accessoryService.updateAccessory(accessoryDTO, productDTO);

        return "redirect:/admin/product-table";
    }

    @PostMapping("/admin/product-table/bird-cage/{productId}/edit")
    public String updateBirdCage(@RequestParam("main-image") MultipartFile fileUploadMain,
                                @RequestParam(value = "extra-image1", required = false) MultipartFile fileUploadExtra1,
                                @RequestParam(value = "extra-image2", required = false) MultipartFile fileUploadExtra2,
                                @RequestParam(value = "extra-image3", required = false) MultipartFile fileUploadExtra3,
                                 @ModelAttribute("birdCageDetail") @Valid BirdCageDTO birdCageDTO,
                                 BindingResult birdCageBindingResult,
                                 @ModelAttribute("birdCage") @Valid ProductDTO productDTO,
                                 BindingResult productBindingResult,
                                 @PathVariable("productId") Long productId) throws IOException {
        BirdCageDetail birdCageDetail = birdCageService.findBirdCageByID(productId);
        
        if (fileUploadMain != null && !fileUploadMain.isEmpty()) {
            String imageURLMain = cloudinaryUpload.uploadFile(fileUploadMain);
            productDTO.setMainImage(imageURLMain);
        } else productDTO.setMainImage(birdCageDetail.getBirdCage().getMainImage());

        if (birdCageBindingResult.hasErrors() || productBindingResult.hasErrors()) {
            return "bird-cage-edit";
        }
        
        if (fileUploadExtra1 != null && !fileUploadExtra1.isEmpty()) {
            String imageURLExtra1 = cloudinaryUpload.uploadFile(fileUploadExtra1);
            productDTO.setExtraImage1(imageURLExtra1);
        }else productDTO.setExtraImage1(birdCageDetail.getBirdCage().getExtraImage1());

        if (fileUploadExtra2 != null && !fileUploadExtra2.isEmpty()) {
            String imageURLExtra2 = cloudinaryUpload.uploadFile(fileUploadExtra2);
            productDTO.setExtraImage2(imageURLExtra2);
        }else productDTO.setExtraImage2(birdCageDetail.getBirdCage().getExtraImage2());

        if (fileUploadExtra3 != null && !fileUploadExtra3.isEmpty()) {
            String imageURLExtra3 = cloudinaryUpload.uploadFile(fileUploadExtra3);
            productDTO.setExtraImage3(imageURLExtra3);
        }else productDTO.setExtraImage3(birdCageDetail.getBirdCage().getExtraImage3());

        birdCageDTO.setId(birdCageDetail.getId());
        productDTO.setId(productId);
        productDTO.setProductType("Bird Cage");
        productDTO.setUnitsOnOrder(birdCageDetail.getBirdCage().getUnitsOnOrder());
        birdCageService.updateBirdCage(birdCageDTO, productDTO);
        return "redirect:/admin/product-table";
    }

    @GetMapping("/admin/users-table")
    @RolesAllowed({"ADMIN"})
    public String getListUsers(Model model,
                               @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                               @RequestParam(name = "pageSize", defaultValue = "8") int pageSize,
                               @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                               @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                               @Param("keyword") String keyword) {
        Page<User> usersPage = userService.findPaginatedUsers(pageNumber, pageSize, sortField, sortDir, keyword);
        model.addAttribute("usersPage", usersPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
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

    @PostMapping("/users/save/{id}")
    public String saveUserEditedByAdmin(User user) {
        if(user.getEmail().isEmpty() || user.getFname().isEmpty() || user.getLname().isEmpty() || user.getAddress().isEmpty() || user.getPhoneNumber().isEmpty()){
            return "redirect:/users/edit/{id}?fail";
        }else if (!Pattern.matches("^(84|0)(9|3|5|7|8)[0-9]{8}$", user.getPhoneNumber())) {
            return "redirect:/users/edit/{id}?phone";
        }
        user.setUpdatedDate(LocalDateTime.now());
        userService.saveUserEditedByAdmin(user);
        return "redirect:/admin/users-table?success";
    }
}

