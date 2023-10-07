package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.dto.BirdCageDTO;
import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;

public interface BirdCageService {
    BirdCageDetail saveBirdCage(BirdCageDTO birdCageDTO, ProductDTO productDTO);

    BirdCageDetail updateBirdCage(BirdCageDTO birdCageDTO, ProductDTO productDTO);

    BirdCageDetail findBirdCageByID(Long productId);

}
