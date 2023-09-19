package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.DTO.Bird_CageDTO;
import com.group3.torikago.Torikago.Shop.model.Bird_Cage;

import java.util.List;

public interface ProductService {
    List<Bird_CageDTO> findAllProducts();
    Bird_Cage saveBirdCage(Bird_Cage birdCage);



    void updateProduct(Bird_CageDTO birdCage);
}
