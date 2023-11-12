package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.model.Voucher;
import com.group3.torikago.Torikago.Shop.service.ProductService;
import com.group3.torikago.Torikago.Shop.service.ShoppingCartService;
import com.group3.torikago.Torikago.Shop.service.UserService;
import com.group3.torikago.Torikago.Shop.service.VoucherService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckOutController {
    
    private ShoppingCartService shoppingCartServices;
    private UserService userService;
    private VoucherService voucherService;
    private ProductService productService;

    @Autowired
    public CheckOutController(ShoppingCartService shoppingCartServices, UserService userService,
            VoucherService voucherService, ProductService productService) {
        this.shoppingCartServices = shoppingCartServices;
        this.userService = userService;
        this.voucherService = voucherService;
        this.productService = productService;
    }
    
    @GetMapping("/torikago/checkout")
    public String checkOut(@AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails, Model model) {
        String email = myUserDetails.getUsername();
        User user = userService.findByEmail(email);
        List<CartItems> cartItems = shoppingCartServices.listCartItems(user);
        List<Voucher> vouchers = voucherService.findAllVouchers();
        if (cartItems.size() != 0) {
            String errorUrl = "";
            double orderWeight = 0;
            for (CartItems cartItem : cartItems) {
                Product product = productService.findProductById(cartItem.getProductId().getId());
                if (product.getUnitsInStock() < cartItem.getQuantity()) {
                    errorUrl = errorUrl + "&q" + product.getId() + "=" + product.getUnitsInStock();
                }
                orderWeight += product.getUnitWeight() * cartItem.getQuantity();
            }
            if (errorUrl.isEmpty()) {
                double shippingFee = 32000;
                if (orderWeight > 0.5) {
                    if (orderWeight * 2 - (int) orderWeight * 2 != 0) {
                        shippingFee += 5000 * (int) orderWeight * 2;
                    } else {
                        shippingFee += 5000 * (int) orderWeight * 2 - 5000;
                    }
                }

                model.addAttribute("vouchers", vouchers);
                model.addAttribute("shippingFee", shippingFee);
                model.addAttribute("cartItems", cartItems);
                model.addAttribute("user", user);
                return "shopping-view-order";
            } else {
                return "redirect:/torikago/cart?error" + errorUrl;
            }
        } else {
            return "redirect:/torikago/cart";
        }
    }
    
    
}
