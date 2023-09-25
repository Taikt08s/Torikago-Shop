package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.dto.RegisterDTO;
import com.group3.torikago.Torikago.Shop.exception.UserNotFoundException;
import com.group3.torikago.Torikago.Shop.model.Role;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.repository.RoleRepository;
import com.group3.torikago.Torikago.Shop.repository.UserRepository;
import com.group3.torikago.Torikago.Shop.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@Service
public class UserImplement implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JavaMailSender mailSender;

    @Autowired
    public UserImplement(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    //fill the user object what ever come from the web to save
    @Override
    public void saveUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setUserName(registerDTO.getUserName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setFname(registerDTO.getFname());
        user.setLname(registerDTO.getLname());
        user.setAddress(registerDTO.getAddress());
        user.setPhoneNumber(registerDTO.getPhoneNumber());
        user.setGender(registerDTO.getGender());
        user.setEnabled(false);
        Role role = roleRepository.findByName("USER");
        user.setRole(role);
//set randomVerificationCode in to a random string with length 64
        String randomVerificationCode = RandomString.make(64);
        user.setVerificationCode(randomVerificationCode);
        userRepository.save(user);
    }

    @Override
    public void sendVerificationEmail(String siteURL, User users) throws MessagingException, UnsupportedEncodingException {
        String subject = "Please kindly confirm your registration.";
        String senderName = "Customer Service Team at Torikago";
        String mailContent = "<p>Hello " + users.getFname() + " " + users.getLname() + ",</p>";
        mailContent += "<p>Please click the button below to verify your email address with Torikago Shop. " +
                "Verifying your email address adds an additional layer of security to your account. " +
                "Maintaining accurate information on file will be beneficial should you require any assistance with your account.</p>";
        String verifyURL = siteURL + "/verify?code=" + users.getVerificationCode();
        mailContent += "<a href='" + verifyURL
                + "' style='background-color: blue; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;'>VERIFY ACCOUNT</a>";
        mailContent += "<p>We wish you a best experience ღゝ◡╹)ノ♡</p>";
        mailContent += "<p>Customer Service team</p></br>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("torikago.customerservice@gmail.com", senderName);
        helper.setTo(users.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent);
        helper.setText(mailContent, true);
        mailSender.send(message);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Cant find user with email: " + email);
        }

    }
    public User get(String resetPasswordToken) {
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodePassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}
