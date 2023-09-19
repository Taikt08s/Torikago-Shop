package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.dto.RegisterDTO;
import com.group3.torikago.Torikago.Shop.model.User;

public interface UserService {
    void saveUser(RegisterDTO registerDTO);

    User findByEmail(String email);

    User findByUsername(String userName);
}
