package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.dto.VoucherDTO;
import com.group3.torikago.Torikago.Shop.model.Voucher;
import com.group3.torikago.Torikago.Shop.service.VoucherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class VoucherController {
//<<<<<<< HEAD
//    private VoucherService voucherService;
//    @Autowired
//    public VoucherController(VoucherService voucherService) {
//        this.voucherService = voucherService;
//    }
//    @GetMapping("/vouchers")
//    public String listVoucher(Model model){
//        List<VoucherDTO> vouchers= voucherService.findAllVouchers();
//        model.addAttribute("vouchers",vouchers);
//        return "manager-voucher";
//    }
//    @GetMapping("/vouchers/new")
//    public String createVoucher(Model model){
//        Voucher voucher=new Voucher();
//        model.addAttribute("voucher",voucher);
//        return "create-voucher";
//    }
//    @PostMapping("/vouchers/new")
//    public String saveVoucher(@Valid @ModelAttribute("voucher") VoucherDTO voucherDTO, BindingResult voucherResult, Model model){
//        if(voucherResult.hasErrors()){
//            model.addAttribute("voucher",voucherDTO);
//            return "create-voucher";
//        }
//        voucherService.saveVoucher(voucherDTO);
//        return "redirect:/vouchers";
//    }
//    @GetMapping("vouchers/{id}")
//    public String voucherDetail(@PathVariable("id") Long id, Model model){
//        VoucherDTO voucherDTO = voucherService.findById(id);
//        model.addAttribute("voucher",voucherDTO);
//        return "voucher-detail";
//    }
//    @GetMapping("/vouchers/edit/{id}")
//    public String editVoucher(@PathVariable("id") Long id, Model model){
//        VoucherDTO voucher = voucherService.findById(id);
//        model.addAttribute("voucher", voucher);
//        return "voucher-edit";
//    }
//    @PostMapping("/vouchers/edit/{id}")
//    public String updateVoucher(@PathVariable("id") Long id,
//                                @Valid @ModelAttribute("voucher") VoucherDTO voucher,
//                                BindingResult voucherResult){
//        if(voucherResult.hasErrors()){
//            return "voucher-edit";
//        }
//=======
//>>>>>>> faae7d58b93891dd2f947be48c0c22eb53c46d2e
//

}
