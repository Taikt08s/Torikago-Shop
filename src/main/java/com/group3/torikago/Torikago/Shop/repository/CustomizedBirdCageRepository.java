package com.group3.torikago.Torikago.Shop.repository;


import com.group3.torikago.Torikago.Shop.model.CustomizedBirdCage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomizedBirdCageRepository extends JpaRepository<CustomizedBirdCage, Long> {
    public CustomizedBirdCage findByCustomizedBirdCage_Id(Long productId);
}
