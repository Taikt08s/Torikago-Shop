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
    public BirdCageDetail saveBirdCage(BirdCageDTO birdCageDTO) {
        BirdCageDetail detail = mapToBirdCage(birdCageDTO);
        return birdCageRepository.save(detail);
    }

    @Override
    public BirdCageDetail updateBirdCage(BirdCageDetail birdCage) {
        return birdCageRepository.save(birdCage);
    }


    private BirdCageDetail mapToBirdCage(BirdCageDTO birdCage) {
        ProductImplement productImplement = new ProductImplement();
        BirdCageDetail birdCageDTO=BirdCageDetail.builder()
                .birdCage(productImplement.mapToProduct(birdCage.getBirdCage()))
                .dimension(birdCage.getDimension())
                .cageShape(birdCage.getCageShape())
                .birdWingSpan(birdCage.getBirdWingSpan())
                .barSpacing(birdCage.getBarSpacing())
                .description(birdCage.getDescription())
                .build();
        return birdCageDTO;
    }
//    private BirdCageDetail mapToBirdCage(BirdCageDTO birdCage) {
//
//        BirdCageDetail birdCageDTO=BirdCageDetail.builder()
//                .birdCage(birdCage.getBirdCage())
//                .dimension(birdCage.getDimension())
//                .cageShape(birdCage.getCageShape())
//                .birdWingSpan(birdCage.getBirdWingSpan())
//                .barSpacing(birdCage.getBarSpacing())
//                .description(birdCage.getDescription())
//                .build();
//        return birdCageDTO;
//    }

}
