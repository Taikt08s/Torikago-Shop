package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.dto.CustomizedBirdCageDTO;
import com.group3.torikago.Torikago.Shop.model.CustomizedBirdCage;
import com.group3.torikago.Torikago.Shop.repository.CustomizedBirdCageRepository;
import com.group3.torikago.Torikago.Shop.service.CustomizedBirdCageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomizedBirdCageImplement implements CustomizedBirdCageService {
    private CustomizedBirdCageRepository customizedBirdCageRepository;

    @Autowired
    public CustomizedBirdCageImplement(CustomizedBirdCageRepository customizedBirdCageRepository) {
        this.customizedBirdCageRepository = customizedBirdCageRepository;
    }

    @Override
    public CustomizedBirdCage saveCustomizedBirdCage(CustomizedBirdCageDTO customizedBirdCageDTO) {
        CustomizedBirdCage detail = mapToCustomizedBirdCage(customizedBirdCageDTO);
        return customizedBirdCageRepository.save(detail);
    }

    @Override
    public CustomizedBirdCage updateCustomizedBirdCage(CustomizedBirdCage customizedBirdCage) {
        return customizedBirdCageRepository.save(customizedBirdCage);
    }

    @Override
    public CustomizedBirdCage findByCustomizedBirdCage_Id(Long productId) {
        CustomizedBirdCage product = customizedBirdCageRepository.findByCustomizedBirdCage_Id(productId);
        return product;
    }


    private CustomizedBirdCage mapToCustomizedBirdCage(CustomizedBirdCageDTO customizedBirdCageDTO) {
        ProductImplement productImplement = new ProductImplement();
        CustomizedBirdCage customizedBirdCage = CustomizedBirdCage.builder()
                .customizedBirdCage(productImplement.mapToProduct(customizedBirdCageDTO.getCustomizedBirdCage()))
                .dimension(customizedBirdCageDTO.getDimension())
                .cageShape(customizedBirdCageDTO.getCageShape())
                .birdWingSpan(customizedBirdCageDTO.getBirdWingSpan())
                .barSpacing(customizedBirdCageDTO.getBarSpacing())
                .description(customizedBirdCageDTO.getDescription())
                .build();
        return customizedBirdCage;
    }
}
