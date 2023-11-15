package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.dto.RegisterDTO;

import com.group3.torikago.Torikago.Shop.model.Role;

import com.group3.torikago.Torikago.Shop.exception.UserNotFoundException;

import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.model.AuthenticationProvider;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    Page<User> findPaginatedUsers(int pageNumber, int pageSize, String sortField, String sortDir, String keyword);

    void saveUser(RegisterDTO registerDTO);

    void sendVerificationEmail(String siteURL, User user) throws MessagingException, UnsupportedEncodingException;

    User findByEmail(String email);

    User findByUsername(String userName);

    boolean verify(String code);

    User get(Long id);

    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

    List<Role> getRoles();

    User saveUserEditedByAdmin(User user);

    User updateAccountOfUser(User user);

    void saveUserChangePassword(User user);

    void createNewUserAfterOauthLogin(String email, String name, AuthenticationProvider provider);

    void updateUserAfterOauthLogin(User user, String name, AuthenticationProvider authenticationProvider);

}
