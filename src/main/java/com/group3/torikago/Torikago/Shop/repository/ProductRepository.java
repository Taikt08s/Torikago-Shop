package com.group3.torikago.Torikago.Shop.repository;

import com.group3.torikago.Torikago.Shop.model.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.productName LIKE %?1% OR "+ "p.productType LIKE %?1%")
    Page<Product> findAll(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.productType like '%Customized%'")
    Page<Product> findAllCustomizedProduct(Pageable pageable);

    Optional<Product> findById(Long productId);

    @Query("SELECT p from Product p WHERE p.productType like '%Customized%' AND p.id IN" + "(SELECT c.productId FROM CartItems c WHERE c.userId.id= ?1 )")
    Page<Product> findAllCustomizedProductByUser(Pageable pageable, Long id);
}
