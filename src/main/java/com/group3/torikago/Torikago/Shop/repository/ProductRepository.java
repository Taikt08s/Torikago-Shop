package com.group3.torikago.Torikago.Shop.repository;

import com.group3.torikago.Torikago.Shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE p.productName LIKE %?1%")
    Page<Product> findAll(String keyword, Pageable pageable);
}
