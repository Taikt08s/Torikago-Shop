package com.group3.torikago.Torikago.Shop.repository;

import com.group3.torikago.Torikago.Shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ShoppingProductsRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p from Product p WHERE p.productName LIKE concat('%', :query,'%') ")
    List<Product> searchClub(String query);

}
