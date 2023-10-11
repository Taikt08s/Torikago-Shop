package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.dto.RegisterDTO;
import com.group3.torikago.Torikago.Shop.model.Role;
import com.group3.torikago.Torikago.Shop.model.User;
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
import java.util.List;

@Controller
public class AuthController {
    private UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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

    @GetMapping("/profile")
    public String editUser(@AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails, Model model) {
        String email = myUserDetails.getUsername();
        User user = userService.findByEmail(email);
        List<Role> listRoles = userService.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user-profile";
    }

    @PostMapping("/profile")
    public String saveUser(User user, HttpSession session) {
        userService.updateAccountOfUser(user);
        user.setFname(user.getFname());
        user.setLname(user.getLname());
        session.setAttribute("updateInformationTime", LocalDateTime.now());
        session.setAttribute("updateProfileSuccess", "Profile information updated");
        return "redirect:/profile?success";
    }

    @GetMapping("/user/profile/password")
    public String openSettings() {
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

        //String encodedOldPassword = passwordEncoder.encode(oldPassword);
        String encodedNewPassword = passwordEncoder.encode(newPassword);

        // Kiểm tra xem mật khẩu cũ có khớp với mật khẩu đã mã hóa hay không
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return "redirect:/user/profile/password?fail";
        } else if (newPassword.isEmpty()) {
            return "redirect:/user/profile/password?isEmpty";
        } else if (!passwordEncoder.matches(confirmPassword, encodedNewPassword)) {
            return "redirect:/user/profile/password?notMatches";
        } else {
            user.setPassword(encodedNewPassword);
            userService.saveUserChangePassword(user);
            session.setAttribute("changePasswordTime", LocalDateTime.now());
            session.setAttribute("changePasswordSuccess", "Password change successfully");
        }
        return "redirect:/user/profile/password?success";
        // Trả về thông báo thành công
    }


}
