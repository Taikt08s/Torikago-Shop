package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.model.Bird_Cage;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.repository.UserRepository;
import com.group3.torikago.Torikago.Shop.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserImplement implements UserService {
private UserRepository userRepository;

    public UserImplement(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
