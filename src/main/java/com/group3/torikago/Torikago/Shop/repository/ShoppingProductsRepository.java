package com.group3.torikago.Torikago.Shop.repository;

import com.group3.torikago.Torikago.Shop.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShoppingProductsRepository extends PagingAndSortingRepository<Product, Long> {
}
