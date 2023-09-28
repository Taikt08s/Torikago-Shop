package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.Product;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProducts();

    Product saveProduct(ProductDTO productDTO);

    void updateProduct(ProductDTO product);

    ProductDTO findProductById(Long productId);
    
    void deleteProduct(Long productId);
}
