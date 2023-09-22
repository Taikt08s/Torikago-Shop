package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.Product;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProducts();
    Product saveBirdCage(Product product);



    void updateProduct(ProductDTO product);
}
