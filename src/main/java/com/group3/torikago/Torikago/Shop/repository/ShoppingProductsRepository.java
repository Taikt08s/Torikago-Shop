package com.group3.torikago.Torikago.Shop.repository;

import com.group3.torikago.Torikago.Shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShoppingProductsRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:search% AND (p.productType LIKE '%Accessory%' OR p.productType LIKE '%Bird%')")
    Page<Product> findAll(@Param("search") String search, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.productType LIKE '%Accessory%' OR p.productType LIKE '%Bird%' ")
    Page<Product> findAll(Integer dummy, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(p.unitPrice >= :priceFrom OR :priceFrom IS NULL) AND " +
            "(p.unitPrice <= :priceTo OR :priceTo IS NULL) AND " +
            "(p.productType LIKE CONCAT('%', :type, '%') OR :type IS NULL)")
    Page<Product> findAllByPriceRangeAndType(@Param("priceFrom") Double priceFrom,
                                      @Param("priceTo") Double priceTo,
                                      @Param("type") String type,
                                      Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE p.id != :productId AND p.productType = 'Bird Cage'")
    List<Product> findAllBirdCagesByIdNot(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p " +
            "WHERE p.id != :productId AND p.productType = 'Accessory'")
    List<Product> findAllAccessoriesByIdNot(@Param("productId") Long productId);

}
