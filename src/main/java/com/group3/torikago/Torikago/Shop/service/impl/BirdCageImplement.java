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
    public BirdCageDetail saveBirdCage(BirdCageDTO birdCageDTO, ProductDTO productDTO) {
        BirdCageDetail detail = mapToBirdCage(birdCageDTO, productDTO);
        return birdCageRepository.save(detail);
    }

    @Override
    public void updateBirdCage(BirdCageDTO birdCageDTO) {
//        BirdCageDetail birdCage=mapToBirdCage(birdCageDTO);
//        birdCageRepository.save(birdCage);
    }


    private BirdCageDetail mapToBirdCage(BirdCageDTO birdCage, ProductDTO productDTO) {
        ProductImplement productImplement = new ProductImplement();
        Product product = productImplement.mapToProduct(productDTO);
        BirdCageDetail birdCageDTO=BirdCageDetail.builder()
                .birdCage(product)
                .dimension(birdCage.getDimension())
                .cageShape(birdCage.getCageShape())
                .birdWingSpan(birdCage.getBirdWingSpan())
                .barSpacing(birdCage.getBarSpacing())
                .description(birdCage.getDescription())
                .build();
        return birdCageDTO;
    }
}
