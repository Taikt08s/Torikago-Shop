package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.dto.BirdCageDTO;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.repository.BirdCageRepository;
import com.group3.torikago.Torikago.Shop.service.BirdCageService;
import org.springframework.stereotype.Service;

@Service
public class BirdCageImplement implements BirdCageService {
    private BirdCageRepository birdCageRepository;

    public BirdCageImplement(BirdCageRepository birdCageRepository) {
        this.birdCageRepository = birdCageRepository;
    }
    @Override
    public BirdCageDetail saveBirdCage(BirdCageDetail birdCageDetail) {
        return birdCageRepository.save(birdCageDetail);
    }

    @Override
    public void updateBirdCage(BirdCageDTO birdCageDTO) {
        BirdCageDetail birdCage=mapToBirdCage(birdCageDTO);
        birdCageRepository.save(birdCage);
    }

    private BirdCageDetail mapToBirdCage(BirdCageDTO birdCage) {
        BirdCageDetail birdCageDTO=BirdCageDetail.builder()
                .birdCage(birdCage.getBirdCage())
                .cageShape(birdCage.getCageShape())
                .birdWingSpan(birdCage.getBirdWingSpan())
                .barSpacing(birdCage.getBarSpacing())
                .description(birdCage.getDescription())
                .build();
        return birdCageDTO;
    }
}
