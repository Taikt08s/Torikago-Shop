package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.dto.RegisterDTO;

import com.group3.torikago.Torikago.Shop.model.Role;

import com.group3.torikago.Torikago.Shop.exception.UserNotFoundException;

import com.group3.torikago.Torikago.Shop.model.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {



    void saveUser(RegisterDTO registerDTO);

    void sendVerificationEmail( String siteURL, User user) throws MessagingException, UnsupportedEncodingException;

    User findByEmail(String email);

    User findByUsername(String userName);

    boolean verify(String code);

    List<User> listAllUsers();
    List<Role> listRoles();
    User get(Long id);




    void updateResetPasswordToken(String token ,String email) throws UserNotFoundException;

}
