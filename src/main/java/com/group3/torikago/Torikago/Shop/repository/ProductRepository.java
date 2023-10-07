package com.group3.torikago.Torikago.Shop.repository;

import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.Product;

import com.group3.torikago.Torikago.Shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE p.productName LIKE %?1%")
    Page<Product> findAll(String keyword, Pageable pageable);
}
