package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.dto.RegisterDTO;

import com.group3.torikago.Torikago.Shop.model.Role;

import com.group3.torikago.Torikago.Shop.exception.UserNotFoundException;

import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.model.AuthenticationProvider;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {


    void saveUser(RegisterDTO registerDTO);

    void sendVerificationEmail(String siteURL, User user) throws MessagingException, UnsupportedEncodingException;

    User findByEmail(String email);

    User findByUsername(String userName);

    boolean verify(String code);

    List<User> listAllUsers(String keyword);

    User get(Long id);

    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

    List<Role> getRoles();

    void saveUserEditedByAdmin(User user);

    User updateAccountOfUser(User user);

    void saveUserChangePassword(User user);

    void createNewUserAfterOauthLogin(String email, String name, AuthenticationProvider provider);

    void updateUserAfterOauthLogin(User user, String name, AuthenticationProvider authenticationProvider);

}
