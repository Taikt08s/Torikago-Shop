package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.dto.RegisterDTO;
import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.Role;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.service.ShoppingCartService;
import com.group3.torikago.Torikago.Shop.service.UserService;
import com.group3.torikago.Torikago.Shop.util.Util;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class AuthController {
    private UserService userService;
    private final PasswordEncoder passwordEncoder;
    private ShoppingCartService shoppingCartServices;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder, ShoppingCartService shoppingCartServices) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.shoppingCartServices = shoppingCartServices;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegisterDTO user = new RegisterDTO();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegisterDTO user,
                           BindingResult result, Model model, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        User existingUserEmail = userService.findByEmail(user.getEmail());
        if (existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }
        User existingUserUsername = userService.findByUsername(user.getUserName());
        if (existingUserUsername != null && existingUserUsername.getUserName() != null && !existingUserUsername.getUserName().isEmpty()) {
            return "redirect:/register?fail";
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        String siteURL = Util.getSiteURL(request);
        User currentUser = userService.findByEmail(user.getEmail());
        userService.sendVerificationEmail(siteURL, currentUser);
        return "redirect:/torikago?success";
    }

    @GetMapping("/verify")
    public String verifyAuthentication(@RequestParam("code") String code) {
        if (userService.verify(code)) {
            return "verification-success";
        } else {
            return "verification-fail";
        }
    }

    @GetMapping("/user/profile")
    public String editUser(@AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails, Model model) {
        String email = myUserDetails.getUsername();
        User user = userService.findByEmail(email);

        List<CartItems> cartItems = shoppingCartServices.listCartItems(user);
        model.addAttribute("cartItems", cartItems);

        List<Role> listRoles = userService.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user-profile";
    }

    @PostMapping("/user/profile")
    public String saveUser(User user, HttpSession session) {
        if(user.getEmail().isEmpty() || user.getFname().isEmpty() || user.getLname().isEmpty() || user.getAddress().isEmpty() || user.getPhoneNumber().isEmpty()){
            return "redirect:/user/profile?fail";
        }else if (!Pattern.matches("^(84|0)(9|3|5|7|8)[0-9]{8}$", user.getPhoneNumber())) {
            return "redirect:/user/profile?phone";
        }
        userService.updateAccountOfUser(user);
        user.setFname(user.getFname());
        user.setLname(user.getLname());
        session.setAttribute("updateInformationTime", LocalDateTime.now());
        session.setAttribute("updateProfileSuccess", "Profile information updated");
        return "redirect:/user/profile?success";
    }

    @GetMapping("/user/profile/password")
    public String openSettings(@AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails, Model model) {
        String email = myUserDetails.getUsername();
        User user = userService.findByEmail(email);
        List<CartItems> cartItems = shoppingCartServices.listCartItems(user);
        model.addAttribute("cartItems", cartItems);
        return "user-profile-password";
    }

    @PostMapping("/user/profile/password/save")
    public String changePassword(@AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails,
                                 @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmNewPassword") String confirmPassword,
                                 HttpSession session) {

        String email = myUserDetails.getUsername();
        User user = userService.findByEmail(email);

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        boolean hasLetter = Pattern.matches(".*[a-zA-Z].*", newPassword);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return "redirect:/user/profile/password?fail";
        } else if (newPassword.isEmpty()) {
            return "redirect:/user/profile/password?isEmpty";
        } else if (!hasLetter) {
            return "redirect:/user/profile/password?letter";
        } else if (newPassword.length() < 8) {
            return "redirect:/user/profile/password?length";
        } else if (!passwordEncoder.matches(confirmPassword, encodedNewPassword)) {
            return "redirect:/user/profile/password?notMatches";

        } else if (!Character.isUpperCase(newPassword.charAt(0))) {
            return "redirect:/user/profile/password?isUpperCase";
        } else {
            user.setPassword(encodedNewPassword);
            user.setUpdatedDate(LocalDateTime.now());
            userService.saveUserChangePassword(user);
            session.setAttribute("changePasswordTime", LocalDateTime.now());
            session.setAttribute("changePasswordSuccess", "Password changed successfully");
        }
        return "redirect:/user/profile/password?success";

    }

}
