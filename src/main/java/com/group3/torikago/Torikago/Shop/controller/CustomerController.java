package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.model.CustomizedBirdCage;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.service.CustomizedBirdCageService;
import com.group3.torikago.Torikago.Shop.service.ProductService;
import com.group3.torikago.Torikago.Shop.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {

    private ProductService productService;

    private UserService userService;

    private CustomizedBirdCageService customizedBirdCageService;
    @Autowired
    public CustomerController(ProductService productService, UserService userService, CustomizedBirdCageService customizedBirdCageService) {
        this.productService = productService;
        this.userService = userService;
        this.customizedBirdCageService = customizedBirdCageService;
    }
    @GetMapping("/customer/customized-product")
    @RolesAllowed({"CUSTOMER"})
    public String customizedProductPage(Model model,                                 @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                        @RequestParam(name = "pageSize", defaultValue = "8") int pageSize,
                                        @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                                        @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                                        @Param("keyword") String keyword,
                                        @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails) {

        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);

        Page<Product> productsPage = productService.findCustomizedProductsByUser(pageNumber, pageSize, sortField, sortDir,keyword, user.getId());
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword",keyword);
        return "customer-customized-product";
    }

    @PostMapping("/customer")
    @RolesAllowed({"CUSTOMER"})
    public String viewCustomizedProduct(){
        return "Hello world again SOS";
    }

    @GetMapping("/customer/customized-product/{productId}/add")
    @RolesAllowed({"CUSTOMER"})
    public String addCustomizedProductToCart(Model model, @PathVariable("productId") Long productId) {
        CustomizedBirdCage customizedBirdCage = customizedBirdCageService.findByCustomizedBirdCage_Id(productId);
        if(customizedBirdCage.getStatus().equals("Pending") || customizedBirdCage.getStatus().equals("Rejected")){
            customizedBirdCage.setCartStatus(false);
            customizedBirdCageService.updateCustomizedBirdCage(customizedBirdCage);
            return "redirect:/customer/customized-product";
        }
        customizedBirdCage.setCartStatus(true);
        customizedBirdCageService.updateCustomizedBirdCage(customizedBirdCage);
        return "redirect:/customer/customized-product";
    }
}
