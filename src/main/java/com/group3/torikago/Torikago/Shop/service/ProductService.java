package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Page<ProductDTO> findPaginatedProducts(int pageNumber, int pageSize, String sortField, String sortDir, String keyword);

    Page<Product> findCustomizedProducts(int pageNumber, int pageSize, String sortField, String sortDir, String keyword);
    
    Product findProductById(Long productId);

    Page<Product> findCustomizedProductsByUser(int pageNumber, int pageSize, String sortField, String sortDir, String keyword, Long id);

}
