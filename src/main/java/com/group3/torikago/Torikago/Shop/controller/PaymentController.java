package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.config.PaymentConfig;
import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.Order;
import com.group3.torikago.Torikago.Shop.model.OrderDetails;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.service.OrderService;
import com.group3.torikago.Torikago.Shop.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import com.group3.torikago.Torikago.Shop.service.ShoppingCartService;

@Controller
public class PaymentController {
    
    private UserService userService;
    private OrderService orderService;
    private ShoppingCartService shoppingCartService;

    @Autowired
    public PaymentController(UserService userService, OrderService orderService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
    }
    
    @GetMapping("/torikago/payment")
    public String getPayment(@AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails) 
            throws UnsupportedEncodingException {
        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);
        double totalPrice = 0;
        List<CartItems> cart = shoppingCartService.listCartItems(user);
        for (CartItems cartItems : cart) {
            totalPrice += cartItems.getSubtotal();
        }
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = (long) (totalPrice * 100);
        String bankCode = "NCB";

        String vnp_TxnRef = PaymentConfig.getRandomNumber(8);
        String vnp_IpAddr = "118.69.182.144";

        String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;
        return "redirect:"+paymentUrl;
    }
    
    @GetMapping("/torikago/payment/info")
    public String saveOrderInfo(@RequestParam("vnp_Amount") String orderValue,
                                  @RequestParam("vnp_BankCode") String bankCode,
                                  @RequestParam("vnp_ResponseCode") String rspCode,
                                  @RequestParam("vnp_OrderInfo") String orderInfo,
                                  @RequestParam("vnp_TransactionStatus") String tranStatus,
                                  @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails){
        if (rspCode.equals("00")) {
        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);       
        orderService.saveOrder(user, orderValue);
        return "redirect:/torikago/payment/success";
        } else {
            return "redirect:/torikago/payment/fail";
        }        
    }
    
    @GetMapping("/torikago/payment/success")
    public String showPaymentSuccess(){
        return "order-bill";
    }
    
    @GetMapping("/torikago/payment/fail")
    public String showPaymentFail(){
        return "payment-failed";
    }

}
