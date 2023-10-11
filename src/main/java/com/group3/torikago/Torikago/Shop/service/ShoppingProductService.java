package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShoppingProductService {
    Page<Product> findPaginatedShoppingProducts(int pageNumber, int pageSize, String sortField, String sortDir, String search);

    Product findProductById(Long id);

}
