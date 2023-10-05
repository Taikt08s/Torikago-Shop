package com.group3.torikago.Torikago.Shop.repository;

import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirdCageRepository extends JpaRepository<BirdCageDetail,Long> {

    public BirdCageDetail findByBirdCage_Id(Long productId);
}
