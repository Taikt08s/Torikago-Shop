package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.model.Product;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {
    @GetMapping("/customer")
    @RolesAllowed({"CUSTOMER"})
    public String managerPage() {
        return "/customer-customized-product";
    }

//    @GetMapping("/customer/customer-customized-product")
//    @RolesAllowed({"CUSTOMER"})
//    public String getListCustomizedProduct(Model model,
//                                           @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
//                                           @RequestParam(name = "pageSize", defaultValue = "8") int pageSize,
//                                           @RequestParam(name = "sortField", defaultValue = "id") String sortField,
//                                           @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
//                                           @Param("keyword") String keyword) {
//        // Use these parameters to fetch a paginated and sorted list of products
//        Page<Product> productsPage = productService.findCustomizedProducts(pageNumber, pageSize, sortField, sortDir,keyword);
//
//        model.addAttribute("productsPage", productsPage);
//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDir", sortDir);
//        model.addAttribute("keyword",keyword);
//        return "manager-custom-orders";
//    }
}
