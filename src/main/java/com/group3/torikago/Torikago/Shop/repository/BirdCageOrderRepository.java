package com.group3.torikago.Torikago.Shop.repository;

import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.BirdCageOrder;
import com.group3.torikago.Torikago.Shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BirdCageOrderRepository extends JpaRepository<BirdCageOrder,Long> {
}
