package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.dto.BirdCageDTO;
import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.repository.BirdCageRepository;
import com.group3.torikago.Torikago.Shop.service.BirdCageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BirdCageImplement implements BirdCageService {
    private BirdCageRepository birdCageRepository;

    @Autowired
    public BirdCageImplement(BirdCageRepository birdCageRepository) {
        this.birdCageRepository = birdCageRepository;
    }

    @Override
    public BirdCageDetail findBirdCageByID(Long productId) {
        BirdCageDetail product = birdCageRepository.findByBirdCage_Id(productId);
        return product;
    }


    @Override
    public BirdCageDetail saveBirdCage(BirdCageDTO birdCageDTO, ProductDTO productDTO) {
        BirdCageDetail birdCage = mapToBirdCage(birdCageDTO, productDTO);
        return birdCageRepository.save(birdCage);
    }

    @Override
    public BirdCageDetail updateBirdCage(BirdCageDTO birdCageDTO, ProductDTO productDTO) {
        BirdCageDetail birdCage = mapToBirdCage(birdCageDTO, productDTO);
        return birdCageRepository.save(birdCage);
    }


    private BirdCageDetail mapToBirdCage(BirdCageDTO birdCageDTO, ProductDTO productDTO) {
        ProductImplement productImplement = new ProductImplement();
        BirdCageDetail birdCage=BirdCageDetail.builder()
                .id(birdCageDTO.getId())
                .birdCage(productImplement.mapToProduct(productDTO))
                .dimension(birdCageDTO.getDimension())
                .cageShape(birdCageDTO.getCageShape())
                .birdWingSpan(birdCageDTO.getBirdWingSpan())
                .barSpacing(birdCageDTO.getBarSpacing())
                .description(birdCageDTO.getDescription())
                .material(birdCageDTO.getMaterial())
                .color(birdCageDTO.getColor())
                .build();
        return birdCage;
    }

}
