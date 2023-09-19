package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.DTO.Bird_CageDTO;
import com.group3.torikago.Torikago.Shop.model.Bird_Cage;
import com.group3.torikago.Torikago.Shop.repository.ProductRepository;
import com.group3.torikago.Torikago.Shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImplement implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductImplement(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Bird_CageDTO> findAllProducts() {
        List<Bird_Cage> birdCages=productRepository.findAll();
        return birdCages.stream().map((birdCage)->mapToBirdcageDTO(birdCage)).collect(Collectors.toList());
    }

    private Bird_CageDTO mapToBirdcageDTO(Bird_Cage birdCage) {
        Bird_CageDTO birdCageDTO=Bird_CageDTO.builder()
                .ProductID(birdCage.getProductID())
                .ProductName(birdCage.getProductName())
                .BirdWingSpan(birdCage.getBirdWingSpan())
                .Status(birdCage.getStatus())
                .CageShape(birdCage.getCageShape())
                .Dimension(birdCage.getDimension())
                .BarSpacing(birdCage.getBarSpacing())
                .UnitPrice(birdCage.getUnitPrice())
                .UnitsOnOrder(birdCage.getUnitsOnOrder())
                .UnitsInStock(birdCage.getUnitsInStock())
                .build();
                return birdCageDTO;
    }

    @Override
    public Bird_Cage saveBirdCage(Bird_Cage bc) {
           return productRepository.save(bc);
    }

//    @Override
//    public Bird_CageDTO findProductById(Long productID) {
//        return null;
//    }

    @Override
    public void updateProduct(Bird_CageDTO birdCageDTO) {
        Bird_Cage birdCage=mapToBirdcage(birdCageDTO);
        productRepository.save(birdCage);
    }

    private Bird_Cage mapToBirdcage(Bird_CageDTO birdCage) {
        Bird_Cage birdCageDTO =Bird_Cage.builder()
                .ProductID(birdCage.getProductID())
                .ProductName(birdCage.getProductName())
                .BirdWingSpan(birdCage.getBirdWingSpan())
                .Status(birdCage.getStatus())
                .CageShape(birdCage.getCageShape())
                .Dimension(birdCage.getDimension())
                .BarSpacing(birdCage.getBarSpacing())
                .UnitPrice(birdCage.getUnitPrice())
                .UnitsOnOrder(birdCage.getUnitsOnOrder())
                .UnitsInStock(birdCage.getUnitsInStock())
                .build();
        return birdCageDTO;
    }


}
