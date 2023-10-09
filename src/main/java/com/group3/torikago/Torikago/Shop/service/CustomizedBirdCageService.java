package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.dto.CustomizedBirdCageDTO;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.CustomizedBirdCage;

public interface CustomizedBirdCageService {
    CustomizedBirdCage saveCustomizedBirdCage(CustomizedBirdCageDTO customizedBirdCageDTO);

    CustomizedBirdCage updateCustomizedBirdCage(CustomizedBirdCage customizedBirdCage);

    CustomizedBirdCage findByCustomizedBirdCage_Id(Long productID);
}