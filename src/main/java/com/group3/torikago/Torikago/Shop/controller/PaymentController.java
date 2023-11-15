package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.config.PaymentConfig;
import com.group3.torikago.Torikago.Shop.model.*;
import com.group3.torikago.Torikago.Shop.service.OrderService;
import com.group3.torikago.Torikago.Shop.service.ProductService;
import com.group3.torikago.Torikago.Shop.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import com.group3.torikago.Torikago.Shop.service.ShoppingCartService;
import com.group3.torikago.Torikago.Shop.service.VoucherService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaymentController {

    private JavaMailSender mailSender;
    private UserService userService;
    private OrderService orderService;
    private ShoppingCartService shoppingCartService;
    private ProductService productService;
    private VoucherService voucherService;

    @Autowired
    public PaymentController(UserService userService, OrderService orderService, VoucherService voucherService,
            ShoppingCartService shoppingCartService, ProductService productService, JavaMailSender mailSender) {
        this.userService = userService;
        this.orderService = orderService;
        this.voucherService = voucherService;
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.mailSender = mailSender;
    }

    @PostMapping("/torikago/payment/vnpay")
    public String getPaymentVNPay(@AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails,
            @RequestParam("discount") double discount)
            throws UnsupportedEncodingException {
        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);
        double totalPrice = 0;
        double orderWeight = 0;
        List<CartItems> cart = shoppingCartService.listCartItems(user);
        for (CartItems cartItem : cart) {
            Product product = productService.findProductById(cartItem.getProductId().getId());
            totalPrice += cartItem.getSubtotal();
            orderWeight += product.getUnitWeight() * cartItem.getQuantity();
        }
        double shippingFee = 32000;
        if (orderWeight > 0.5) {
            if (orderWeight * 2 - (int) orderWeight * 2 != 0) {
                shippingFee += 5000 * (int) orderWeight * 2;
            } else {
                shippingFee += 5000 * (int) orderWeight * 2 - 5000;
            }
        }
        totalPrice += shippingFee;
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = (long) ((totalPrice - discount) * 100);
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

        vnp_Params.put("vnp_Locale", "en");
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String vnp_CreateDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
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
        return "redirect:" + paymentUrl;
    }

    @GetMapping("/torikago/payment/vnpay/info")
    public String saveOrderInfoVNPay(@RequestParam("vnp_Amount") String orderValue,
            @RequestParam("vnp_ResponseCode") String rspCode,
            @RequestParam("vnp_OrderInfo") String orderInfo,
            @RequestParam("vnp_TransactionStatus") String tranStatus,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails)
            throws MessagingException, UnsupportedEncodingException {
        if (rspCode.equals("00")) {
            String userName = myUserDetails.getUsername();
            User user = userService.findByEmail(userName);
            Order order = orderService.saveOrderVNPay(user, orderValue);
            sendEmailAfterOrderSuccessfully(myUserDetails, order);
            return "redirect:/torikago/payment/success";
        } else {
            return "redirect:/torikago/payment/fail";
        }
    }

    @PostMapping("/torikago/payment/cod")
    public String saveOrderInfoCod(@RequestParam("amount") double orderValue,
            @RequestParam("shipping_Fee") double shippingFee,
            @RequestParam("discount") double discount,
            @RequestParam(value = "voucherId", required = false) Long voucherId,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails)
            throws MessagingException, UnsupportedEncodingException {
        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);
        Voucher voucher = null;
        if (voucherId != null) {
            voucher = voucherService.findByVId(voucherId);
        }   
        Order order = orderService.saveOrderCod(user, orderValue - discount, shippingFee, voucher);
        sendEmailAfterOrderSuccessfully(myUserDetails, order);
        return "redirect:/torikago/payment/success";
    }

    @GetMapping("/torikago/payment/success")
    public String showPaymentSuccess(@AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails,
            Model model) {
        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);
        List<CartItems> cartItems = shoppingCartService.listCartItems(user);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("user", user);
        return "shopping-order-success";
    }

    @GetMapping("/torikago/payment/fail")
    public String showPaymentFail(@AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails,
            Model model) {
        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);
        List<CartItems> cartItems = shoppingCartService.listCartItems(user);

        model.addAttribute("cartItems", cartItems);
        return "shopping-order-failed";
    }

    private void sendEmailAfterOrderSuccessfully(@AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails,
            Order order) throws MessagingException, UnsupportedEncodingException {

        String userName = myUserDetails.getUsername();
        User user = userService.findByEmail(userName);
        double orderSubtotal = 0.0;
        for (OrderDetails orderDetails : order.getOrderdetails()) {
            double productPrice = orderDetails.getProduct().getUnitPrice();
            int quantity = orderDetails.getQuantity();
            orderSubtotal += productPrice * quantity;
        }
        String subject = "[Torikago Shop] Order Information " + order.getId();
        String senderName = "Customer Service Team at Torikago";
        StringBuilder mailContent = new StringBuilder();
        mailContent.append("<body class=\"body\" style=\"width:100%;height:100%;padding:0;Margin:0\">\n"
                + "<div dir=\"ltr\" class=\"es-wrapper-color\" lang=\"und\" style=\"background-color:#B3E0F2\"> <!--[if gte mso 9]>\n"
                + "    <v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n"
                + "        <v:fill type=\"tile\" color=\"#B3E0F2\"></v:fill>\n"
                + "    </v:background><![endif]-->\n"
                + "    <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"none\"\n"
                + "           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#B3E0F2\">\n"
                + "        <tr>\n"
                + "            <td valign=\"top\" style=\"padding:0;Margin:0\">\n"
                + "                <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" role=\"none\"\n"
                + "                       style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important;background-color:transparent;background-repeat:repeat;background-position:center top\">\n"
                + "                    <tr>\n"
                + "                        <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                + "                            <table bgcolor=\"#ffffff\" class=\"es-header-body\" align=\"center\" cellpadding=\"0\"\n"
                + "                                   cellspacing=\"0\" role=\"none\"\n"
                + "                                   style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\">\n"
                + "                                <tr>\n"
                + "                                    <td class=\"es-m-p0b\" align=\"left\" style=\"padding:20px;Margin:0\"> <!--[if mso]>\n"
                + "                                        <table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\">\n"
                + "                                            <tr>\n"
                + "                                                <td style=\"width:281px\" valign=\"top\"><![endif]-->\n"
                + "                                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" role=\"none\"\n"
                + "                                               style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n"
                + "                                            <tr>\n"
                + "                                                <td class=\"es-m-p0r es-m-p20b\" valign=\"top\" align=\"center\"\n"
                + "                                                    style=\"padding:0;Margin:0;width:261px\">\n"
                + "                                                    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n"
                + "                                                           role=\"presentation\"\n"
                + "                                                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"left\" class=\"es-m-txt-c\"\n"
                + "                                                                style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:5px;font-size:0px\">\n"
                + "                                                                <a target=\"_blank\"\n"
                + "                                                                   style=\"mso-line-height-rule:exactly;text-decoration:underline;color:#222222;font-size:14px\"><img\n"
                + "                                                                        src=\"https://i.imgur.com/LxmV4TW.png\"\n"
                + "                                                                        alt=\"Logo\"\n"
                + "                                                                        style=\"display:block;font-size:16px;border:0;outline:none;text-decoration:none\"\n"
                + "                                                                        height=\"60\" title=\"Logo\" width=\"60\"></a>\n"
                + "                                                            </td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>\n"
                + "                                                </td>\n"
                + "                                                <td class=\"es-hidden\" style=\"padding:0;Margin:0;width:20px\"></td>\n"
                + "                                            </tr>\n"
                + "                                        </table>\n"
                + "                                </tr>\n"
                + "                            </table>\n"
                + "                        </td>\n"
                + "                    </tr>\n"
                + "                </table>\n"
                + "                <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" role=\"none\"\n"
                + "                       style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important\">\n"
                + "                    <tr>\n"
                + "                        <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                + "                            <table class=\"es-content-body\"\n"
                + "                                   style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\"\n"
                + "                                   cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" role=\"none\">\n"
                + "                                <tr>\n"
                + "                                    <td align=\"left\" style=\"padding:0;Margin:0\">\n"
                + "                                        <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" role=\"none\"\n"
                + "                                               style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                            <tr>\n"
                + "                                                <td class=\"es-m-p0r\" valign=\"top\" align=\"center\"\n"
                + "                                                    style=\"padding:0;Margin:0;width:600px\">\n"
                + "                                                    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"\n"
                + "                                                           role=\"presentation\"\n"
                + "                                                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"center\"\n"
                + "                                                                style=\"padding:0;Margin:0;position:relative\"><img\n"
                + "                                                                    class=\"adapt-img\"\n"
                + "                                                                    src=\"https://ecoswzi.stripocdn.email/content/guids/bannerImgGuid/images/image16788672966342121.png\"\n"
                + "                                                                    alt=\"\" title=\"\" width=\"600\"\n"
                + "                                                                    style=\"display:block;font-size:16px;border:0;outline:none;text-decoration:none\"\n"
                + "                                                                    height=\"200\"></td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>\n"
                + "                                                </td>\n"
                + "                                            </tr>\n"
                + "                                        </table>\n"
                + "                                    </td>\n"
                + "                                </tr>\n"
                + "                                <tr>\n"
                + "                                    <td align=\"left\"\n"
                + "                                        style=\"Margin:0;padding-top:30px;padding-right:20px;padding-bottom:30px;padding-left:20px;background-color:#4e73df\">\n"
                + "                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\"\n"
                + "                                               style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                            <tr>\n"
                + "                                                <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n"
                + "                                                    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n"
                + "                                                           role=\"presentation\"\n"
                + "                                                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"center\" class=\"es-m-txt-c\"\n"
                + "                                                                style=\"padding:10px;Margin:0\"><h3\n"
                + "                                                                    style=\"Margin:0;font-family:Raleway, Arial, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:24px;font-style:normal;font-weight:normal;line-height:29px;color:#ffffff\">\n"
                + "                                                                Hello " + user.getFname() + " " + user.getLname() + ",</h3></td>\n"
                + "                                                        </tr>\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"center\" class=\"es-m-txt-c\"\n"
                + "                                                                style=\"padding:0;Margin:0;padding-top:20px\"><p\n"
                + "                                                                    style=\"Margin:0;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;line-height:24px;letter-spacing:0;color:#ffffff;font-size:16px\">\n"
                + "                                                                Thank you for your recent order. We are pleased to\n"
                + "                                                                confirm that we have received your order and it is\n"
                + "                                                                currently being processed.</p></td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>\n"
                + "                                                </td>\n"
                + "                                            </tr>\n"
                + "                                        </table>\n"
                + "                                    </td>\n"
                + "                                </tr>\n"
                + "                            </table>\n"
                + "                        </td>\n"
                + "                    </tr>\n"
                + "                </table>\n"
                + "                <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" role=\"none\"\n"
                + "                       style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:100%;table-layout:fixed !important\">\n"
                + "                    <tr>\n"
                + "                        <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                + "                            <table bgcolor=\"#ffffff\" class=\"es-content-body\" align=\"center\" cellpadding=\"0\"\n"
                + "                                   cellspacing=\"0\" role=\"none\"\n"
                + "                                   style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px\">\n"
                + "                                <tr>\n"
                + "                                    <td align=\"left\"\n"
                + "                                        style=\"Margin:0;padding-right:20px;padding-bottom:30px;padding-left:20px;padding-top:40px\">\n"
                + "                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\"\n"
                + "                                               style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                            <tr>\n"
                + "                                                <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n"
                + "                                                    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n"
                + "                                                           role=\"presentation\"\n"
                + "                                                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"center\" style=\"padding:0;Margin:0\"><h1\n"
                + "                                                                    style=\"Margin:0;font-family:Raleway, Arial, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:50px;font-style:normal;font-weight:normal;line-height:60px;color:#001F3F\">\n"
                + "                                                                Order summary</h1></td>\n"
                + "                                                        </tr>\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"center\" class=\"es-m-p10t\"\n"
                + "                                                                style=\"padding:0;Margin:0;padding-right:20px;padding-left:20px;padding-top:40px\">\n"
                + "                                                                <h3 class=\"b_title\"\n"
                + "                                                                    style=\"Margin:0;font-family:Raleway, Arial, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:24px;font-style:normal;font-weight:normal;line-height:29px;color:#001F3F\">\n"
                + "                                                                    ORDER NO.&nbsp;" + order.getId() + "</h3></td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>\n"
                + "                                                </td>\n"
                + "                                            </tr>\n"
                + "                                        </table>\n"
                + "                                    </td>\n"
                + "                                </tr>\n");
        for (OrderDetails orderDetails : order.getOrderdetails()) {
            mailContent.append("                                <tr>\n"
                    + "                             <td align=\"left\"\n"
                    + "                                        style=\"padding:0;Margin:0;padding-right:20px;padding-left:20px;padding-bottom:40px\">\n"
                    + "                                        <!--[if mso]>\n"
                    + "                                        <table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\">\n"
                    + "                                            <tr>\n"
                    + "                                                <td style=\"width:195px\" valign=\"top\"><![endif]-->\n"
                    + "                                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" role=\"none\"\n"
                    + "                                               style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n"
                    + "                                            <tr>\n"
                    + "                                                <td align=\"left\" class=\"es-m-p20b\"\n"
                    + "                                                    style=\"padding:0;Margin:0;width:195px\">\n"
                    + "                                                    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n"
                    + "                                                           role=\"presentation\"\n"
                    + "                                                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                    + "                                                        <tr>\n"
                    + "                                                            <td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\">\n"
                    + "                                                                <a target=\"_blank\""
                    + "                                                                   style=\"mso-line-height-rule:exactly;text-decoration:underline;color:#6A994E;font-size:16px\"><img\n"
                    + "                                                                        class=\"adapt-img p_image\"\n"
                    + "                                                                        src=\"" + orderDetails.getProduct().getMainImage() + "\"\n"
                    + "                                                                        alt=\"\"\n"
                    + "                                                                        style=\"display:block;font-size:16px;border:0;outline:none;text-decoration:none;border-radius:10px\"\n"
                    + "                                                                        width=\"195\" height=\"195\"></a></td>\n"
                    + "                                                        </tr>\n"
                    + "                                                    </table>\n"
                    + "                                                </td>\n"
                    + "                                            </tr>\n"
                    + "                                        </table>\n"
                    + "                                        <!--[if mso]></td>\n"
                    + "                                    <td style=\"width:20px\"></td>\n"
                    + "                                    <td style=\"width:345px\" valign=\"top\"><![endif]-->\n"
                    + "                                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\"\n"
                    + "                                               role=\"none\"\n"
                    + "                                               style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\n"
                    + "                                                      <tr>\n"
                    + "                <td align=\"left\" style=\"padding:0;Margin:0;width:345px\">\n"
                    + "                    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n"
                    + "                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-left:1px solid #386641;border-right:1px solid #386641;border-top:1px solid #386641;border-bottom:1px solid #386641;border-radius:10px\"\n"
                    + "                           role=\"presentation\">\n"
                    + "                        <tr>\n"
                    + "                            <td align=\"left\" class=\"es-m-txt-c\"\n"
                    + "                                style=\"Margin:0;padding-right:20px;padding-left:20px;padding-top:25px;padding-bottom:25px\">\n"
                    + "                                <h3 class=\"p_name\"\n"
                    + "                                    style=\"Margin:0;font-family:Raleway, Arial, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:24px;font-style:normal;font-weight:normal;line-height:36px;color:#001F3F\">\n"
                    + "                                    " + orderDetails.getProduct().getProductName() + "</h3>\n"
                    + "                                <p style=\"Margin:0;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;line-height:24px;letter-spacing:0;color:#4D4D4D;font-size:16px\">\n"
                    + "                                    WGT: " + formatWeight(orderDetails.getProduct().getUnitWeight()) + "</p>\n"
                    + "                                <p style=\"Margin:0;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;line-height:24px;letter-spacing:0;color:#4D4D4D;font-size:16px\">\n"
                    + "                                    QTY:&nbsp;" + orderDetails.getQuantity() + "</p>\n"
                    + "                                <h3 style=\"Margin:0;font-family:Raleway, Arial, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:24px;font-style:normal;font-weight:normal;line-height:36px;color:#001F3F\"\n"
                    + "                                    class=\"p_price\">" + formatCurrency(orderDetails.getProduct().getUnitPrice()) + "</h3></td>\n"
                    + "                        </tr>\n"
                    + "                    </table>\n"
                    + "                </td>\n"
                    + "            </tr>\n"
                    + "                                        </table>\n"
                    + "                                        <!--[if mso]></td></tr></table><![endif]--></td>\n"
                    + "                                </tr>\n");
        }
        mailContent.append("                                <tr>\n"
                + "                                    <td align=\"left\"\n"
                + "                                        style=\"Margin:0;padding-right:20px;padding-bottom:30px;padding-left:20px;padding-top:40px\">\n"
                + "                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\"\n"
                + "                                               style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                            <tr>\n"
                + "                                                <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n"
                + "                                                    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n"
                + "                                                           role=\"presentation\"\n"
                + "                                                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"center\" style=\"padding:0;Margin:0\"><h1\n"
                + "                                                                    style=\"Margin:0;font-family:Raleway, Arial, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:50px;font-style:normal;font-weight:normal;line-height:60px;color:#001F3F\">\n"
                + "                                                                Order total</h1></td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>\n"
                + "                                                </td>\n"
                + "                                            </tr>\n"
                + "                                        </table>\n"
                + "                                    </td>\n"
                + "                                </tr>\n"
                + "                                <tr>\n"
                + "                                    <td class=\"esdev-adapt-off\" align=\"left\" style=\"padding:20px;Margin:0\">\n"
                + "                                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"esdev-mso-table\" role=\"none\"\n"
                + "                                               style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:560px\">\n"
                + "                                            <tr>\n"
                + "                                                <td class=\"esdev-mso-td\" valign=\"top\" style=\"padding:0;Margin:0\">\n"
                + "                                                    <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\"\n"
                + "                                                           role=\"none\"\n"
                + "                                                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\n"
                + "                                                                <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n"
                + "                                                                       role=\"presentation\"\n"
                + "                                                                       style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                                                    <tr>\n"
                + "                                                                        <td align=\"left\" style=\"padding:0;Margin:0\"><p\n"
                + "                                                                                style=\"Margin:0;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;line-height:24px;letter-spacing:0;color:#4D4D4D;font-size:16px\">\n"
                + "                                                                            Subtotal<br>Discount<br>Shipping</p></td>\n"
                + "                                                                    </tr>\n"
                + "                                                                </table>\n"
                + "                                                            </td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>\n"
                + "                                                </td>\n"
                + "                                                <td style=\"padding:0;Margin:0;width:20px\"></td>\n"
                + "                                                <td class=\"esdev-mso-td\" valign=\"top\" style=\"padding:0;Margin:0\">\n"
                + "                                                    <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\"\n"
                + "                                                           align=\"right\" role=\"none\"\n"
                + "                                                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\n"
                + "                                                                <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n"
                + "                                                                       role=\"presentation\"\n"
                + "                                                                       style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                                                    <tr>\n"
                + "                                                                        <td align=\"right\" style=\"padding:0;Margin:0\"><p\n"
                + "                                                                                style=\"Margin:0;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;line-height:24px;letter-spacing:0;color:#001F3F;font-size:16px\">\n"
                + "                                                                            " + formatCurrency(orderSubtotal) + "<br>$00.00<br>" + formatCurrency(order.getShippingFee()) + "</p></td>\n"
                + "                                                                    </tr>\n"
                + "                                                                </table>\n"
                + "                                                            </td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>\n"
                + "                                                </td>\n"
                + "                                            </tr>\n"
                + "                                        </table>\n"
                + "                                    </td>\n"
                + "                                </tr>\n"
                + "                                <tr>\n"
                + "                                    <td align=\"left\" style=\"padding:0;Margin:0;padding-right:20px;padding-left:20px\">\n"
                + "                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\"\n"
                + "                                               style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                            <tr>\n"
                + "                                                <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n"
                + "                                                    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n"
                + "                                                           role=\"presentation\"\n"
                + "                                                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"center\"\n"
                + "                                                                style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:5px;font-size:0\">\n"
                + "                                                                <table border=\"0\" width=\"100%\" height=\"100%\"\n"
                + "                                                                       cellpadding=\"0\" cellspacing=\"0\"\n"
                + "                                                                       role=\"presentation\"\n"
                + "                                                                       style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                                                    <tr>\n"
                + "                                                                        <td style=\"padding:0;Margin:0;border-bottom:5px dotted #ADD8E6;background:unset;height:1px;width:100%;margin:0px\"></td>\n"
                + "                                                                    </tr>\n"
                + "                                                                </table>\n"
                + "                                                            </td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>\n"
                + "                                                </td>\n"
                + "                                            </tr>\n"
                + "                                        </table>\n"
                + "                                    </td>\n"
                + "                                </tr>\n"
                + "                                <tr>\n"
                + "                                    <td class=\"esdev-adapt-off\" align=\"left\" style=\"padding:20px;Margin:0\">\n"
                + "                                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"esdev-mso-table\" role=\"none\"\n"
                + "                                               style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:560px\">\n"
                + "                                            <tr>\n"
                + "                                                <td class=\"esdev-mso-td\" valign=\"top\" style=\"padding:0;Margin:0\">\n"
                + "                                                    <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\"\n"
                + "                                                           role=\"none\"\n"
                + "                                                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\n"
                + "                                                                <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n"
                + "                                                                       role=\"presentation\"\n"
                + "                                                                       style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                                                    <tr>\n"
                + "                                                                        <td align=\"left\" class=\"es-m-txt-l\"\n"
                + "                                                                            style=\"padding:0;Margin:0\"><h3\n"
                + "                                                                                style=\"Margin:0;font-family:Raleway, Arial, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:24px;font-style:normal;font-weight:normal;line-height:29px;color:#001F3F\">\n"
                + "                                                                            Total</h3></td>\n"
                + "                                                                    </tr>\n"
                + "                                                                </table>\n"
                + "                                                            </td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>\n"
                + "                                                </td>\n"
                + "                                                <td style=\"padding:0;Margin:0;width:20px\"></td>\n"
                + "                                                <td class=\"esdev-mso-td\" valign=\"top\" style=\"padding:0;Margin:0\">\n"
                + "                                                    <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\"\n"
                + "                                                           align=\"right\" role=\"none\"\n"
                + "                                                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\n"
                + "                                                                <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n"
                + "                                                                       role=\"presentation\"\n"
                + "                                                                       style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                                                    <tr>\n"
                + "                                                                        <td align=\"right\" class=\"es-m-txt-r\"\n"
                + "                                                                            style=\"padding:0;Margin:0\"><h3\n"
                + "                                                                                style=\"Margin:0;font-family:Raleway, Arial, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:24px;font-style:normal;font-weight:normal;line-height:29px;color:#386641\">\n"
                + "                                                                            " + formatCurrency(order.getOrderValue()) + "</h3></td>\n"
                + "                                                                    </tr>\n"
                + "                                                                </table>\n"
                + "                                                            </td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>\n"
                + "                                                </td>\n"
                + "                                            </tr>\n"
                + "                                        </table>\n"
                + "                                    </td>\n"
                + "                                </tr>\n"
                + "                                <tr>\n"
                + "                                    <td align=\"left\"\n"
                + "                                        style=\"Margin:0;padding-right:20px;padding-bottom:30px;padding-left:20px;padding-top:40px\">\n"
                + "                                        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\"\n"
                + "                                               style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                            <tr>\n"
                + "                                                <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n"
                + "                                                    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n"
                + "                                                           role=\"presentation\"\n"
                + "                                                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"center\" style=\"padding:0;Margin:0\"><h1\n"
                + "                                                                    style=\"Margin:0;font-family:Raleway, Arial, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:50px;font-style:normal;font-weight:normal;line-height:60px;color:#001F3F\">\n"
                + "                                                                Billing and shipping</h1></td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>\n"
                + "                                                </td>\n"
                + "                                            </tr>\n"
                + "                                        </table>\n"
                + "                                    </td>\n"
                + "                                </tr>\n"
                + "                                <tr>\n"
                + "                                    <td align=\"left\" style=\"padding:20px;Margin:0\"> <!--[if mso]>\n"
                + "                                        <table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\">\n"
                + "                                            <tr>\n"
                + "                                                <td style=\"width:270px\" valign=\"top\"><![endif]-->\n"
                + "                                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" role=\"none\"\n"
                + "                                               style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n"
                + "                                            <tr>\n"
                + "                                                <td class=\"es-m-p20b\" align=\"left\"\n"
                + "                                                    style=\"padding:0;Margin:0;width:270px\">\n"
                + "                                                    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n"
                + "                                                           role=\"presentation\"\n"
                + "                                                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"left\" style=\"padding:0;Margin:0\"><h3\n"
                + "                                                                    style=\"Margin:0;font-family:Raleway, Arial, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:24px;font-style:normal;font-weight:normal;line-height:29px;color:#001F3F\">\n"
                + "                                                                Payment&nbsp;Method</h3>\n"
                + "                                                                <p style=\"Margin:0;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;line-height:24px;letter-spacing:0;color:#4D4D4D;font-size:16px\">\n"
                + "                                                                    " + order.getPaymentMethod() + "</p></td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>\n"
                + "                                                </td>\n"
                + "                                            </tr>\n"
                + "                                        </table>\n"
                + "                                        <!--[if mso]></td>\n"
                + "                                        <td style=\"width:20px\"></td>\n"
                + "                                        <td style=\"width:270px\" valign=\"top\"><![endif]-->\n"
                + "                                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\"\n"
                + "                                               role=\"none\"\n"
                + "                                               style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\n"
                + "                                            <tr>\n"
                + "                                                <td align=\"left\" style=\"padding:0;Margin:0;width:270px\">\n"
                + "                                                    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"\n"
                + "                                                           role=\"presentation\"\n"
                + "                                                           style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                + "                                                        <tr>\n"
                + "                                                            <td align=\"left\" style=\"padding:0;Margin:0\"><h3\n"
                + "                                                                    style=\"Margin:0;font-family:Raleway, Arial, sans-serif;mso-line-height-rule:exactly;letter-spacing:0;font-size:24px;font-style:normal;font-weight:normal;line-height:29px;color:#001F3F\">\n"
                + "                                                                Shipping Address</h3>\n"
                + "                                                                <p style=\"Margin:0;mso-line-height-rule:exactly;font-family:tahoma, verdana, segoe, sans-serif;line-height:24px;letter-spacing:0;color:#4D4D4D;font-size:16px\">\n"
                + "                                                                   " + order.getShippedAddress() + "</p></td>\n"
                + "                                                        </tr>\n"
                + "                                                    </table>\n"
                + "                                                </td>\n"
                + "                                            </tr>\n"
                + "                                        </table>\n"
                + "                                        <!--[if mso]></td></tr></table><![endif]--></td>\n"
                + "                                </tr>\n"
                + "                            </table>\n"
                + "                        </td>\n"
                + "                    </tr>\n"
                + "                </table>\n"
                + "            </td>\n"
                + "        </tr>\n"
                + "    </table>\n"
                + "</div>\n"
                + "</body>");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("styematic@gmail.com", senderName);
        helper.setTo(myUserDetails.getUsername());
        helper.setSubject(subject);
        helper.setText(mailContent.toString(), true);

        mailSender.send(message);
    }

    private String formatWeight(double weight) {
        return String.format("%.0f KG", weight);
    }

    private String formatCurrency(double amount) {
        Locale locale = new Locale("vi", "VN");
        Currency currency = Currency.getInstance(locale);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        currencyFormat.setCurrency(currency);

        return currencyFormat.format(amount);
    }
}
