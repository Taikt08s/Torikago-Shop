package com.group3.torikago.Torikago.Shop.service;

<<<<<<< HEAD
import com.group3.torikago.Torikago.Shop.model.Bird_Cage;

import java.util.List;

public interface UserService {





=======
import com.group3.torikago.Torikago.Shop.dto.RegisterDTO;
import com.group3.torikago.Torikago.Shop.model.User;

public interface UserService {
    void saveUser(RegisterDTO registerDTO);

    User findByEmail(String email);

    User findByUsername(String userName);
>>>>>>> b30e9d69f466fba3d77974c918771e4e0ee9317b
}
