package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.dto.BirdCageDTO;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;

public interface BirdCageService {
    BirdCageDetail saveBirdCage(BirdCageDetail birdCageDetail);

    void updateBirdCage(BirdCageDTO birdCage);
}
