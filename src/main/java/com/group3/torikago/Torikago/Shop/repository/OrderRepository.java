package com.group3.torikago.Torikago.Shop.repository;

import com.group3.torikago.Torikago.Shop.model.Order;
import com.group3.torikago.Torikago.Shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrder(User user);

    @Query("SELECT p FROM Product p WHERE p.productName LIKE %?1%")
    Page<Order> findAll(String keyword, Pageable pageable);
}
