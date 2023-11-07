package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.dto.CustomizedBirdCageDTO;
import com.group3.torikago.Torikago.Shop.dto.ProductDTO;

import com.group3.torikago.Torikago.Shop.dto.VoucherDTO;
import com.group3.torikago.Torikago.Shop.model.*;

import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.CustomizedBirdCage;
import com.group3.torikago.Torikago.Shop.model.Order;
import com.group3.torikago.Torikago.Shop.model.Product;

import com.group3.torikago.Torikago.Shop.service.CustomizedBirdCageService;
import com.group3.torikago.Torikago.Shop.service.OrderService;
import com.group3.torikago.Torikago.Shop.service.ProductService;
import com.group3.torikago.Torikago.Shop.service.VoucherService;
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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


@Controller
public class ManagerController {

    private ProductService productService;
    private VoucherService voucherService;
    private CustomizedBirdCageService customizedBirdCageService;
    private OrderService orderService;
    @Autowired
    public ManagerController(ProductService productService, CustomizedBirdCageService customizedBirdCageService,VoucherService voucherService, OrderService orderService)
    {
        this.customizedBirdCageService = customizedBirdCageService;
        this.productService = productService;
        this.voucherService = voucherService;
        this.orderService = orderService;
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
            // send email

        }
        else{

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

    @RolesAllowed({"MANAGER"})
    @GetMapping("/manager/vouchers")
    public String listVoucher(Model model,
                              @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                              @RequestParam(name = "pageSize", defaultValue = "8") int pageSize,
                              @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                              @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                              @Param("keyword") String keyword){
        Page<VoucherDTO> vouchers= voucherService.findPaginatedVouchers(pageNumber, pageSize, sortField, sortDir, keyword);
        model.addAttribute("vouchers",vouchers);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        return "manager-voucher";
    }
    @RolesAllowed({"MANAGER"})
    @GetMapping("/manager/vouchers/new")
    public String createVoucher(Model model){
        Voucher voucher=new Voucher();
        model.addAttribute("voucher",voucher);
        return "create-voucher";
    }
    @PostMapping("/manager/vouchers/new")
    public String saveVoucher(@Valid @ModelAttribute("voucher") VoucherDTO voucherDTO, BindingResult voucherResult, Model model){
        if(voucherResult.hasErrors()){
            model.addAttribute("voucher",voucherDTO);
            return "create-voucher";
        }
        voucherService.saveVoucher(voucherDTO);
        return "redirect:/vouchers";
    }
    @RolesAllowed({"MANAGER"})
    @GetMapping("/manager/vouchers/{id}")
    public String voucherDetail(@PathVariable("id") Long id, Model model){
        VoucherDTO voucherDTO = voucherService.findById(id);
        model.addAttribute("voucher",voucherDTO);
        return "voucher-detail";
    }
    @RolesAllowed({"MANAGER"})
    @GetMapping("/manager/vouchers/edit/{id}")
    public String editVoucher(@PathVariable("id") Long id, Model model){
        VoucherDTO voucher = voucherService.findById(id);
        model.addAttribute("voucher", voucher);
        return "voucher-edit";
    }
    @PostMapping("/manager/vouchers/edit/{id}")
    public String updateVoucher(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("voucher") VoucherDTO voucher,
                                BindingResult voucherResult){
        if(voucherResult.hasErrors()){
            return "voucher-edit";
        }

        voucher.setId(id);
        if(voucher.getVoucherName() == null || voucher.getVoucherName().isEmpty()){
            voucher.setVoucherName(voucherService.findById(id).getVoucherName());
        }
        if(voucher.getCreatedTime() == null){
            voucher.setCreatedTime(voucherService.findById(id).getCreatedTime());
        }
        if(voucher.getExpiredTime() == null){
            voucher.setExpiredTime(voucherService.findById(id).getExpiredTime());
        }
//        if(voucher.getVoucherValue() == 0.0){
//            voucher.setVoucherValue(voucherService.findById(id).getVoucherValue());
//        }

        voucherService.updateVoucher(voucher);
        return "redirect:/vouchers";
    }
    @RolesAllowed({"MANAGER"})
    @GetMapping("/manager/vouchers/delete/{id}")
    public String deleteVoucher(@PathVariable("id") Long id,Model model) {
        voucherService.deleteVoucher(id);
        return "redirect:/vouchers";
    }
    
    @GetMapping("/manager/orders")
    @RolesAllowed({"MANAGER"})
    public String getListOrder(Model model,
                        @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                        @RequestParam(name = "pageSize", defaultValue = "8") int pageSize,
                        @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                        @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                        @Param("keyword") String keyword) {
        Page<Order> orders = orderService.findPaginatedOrders(pageNumber, pageSize, sortField, sortDir, keyword);
        model.addAttribute("orders", orders);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        return "manager-order";
    }
    
    @GetMapping("/manager/orders/{orderId}/edit")
    @RolesAllowed({"MANAGER"})
    public String editOrder(Model model, @PathVariable("orderId") Long orderId) {
        return "";

    }
}
