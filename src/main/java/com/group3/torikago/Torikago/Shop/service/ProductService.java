package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
//    List<ProductDTO> findAllProducts();

    ProductDTO findProductById(Long productId);

    Page<ProductDTO> findPaginatedProducts(int pageNumber, int pageSize, String sortField, String sortDir);
}
