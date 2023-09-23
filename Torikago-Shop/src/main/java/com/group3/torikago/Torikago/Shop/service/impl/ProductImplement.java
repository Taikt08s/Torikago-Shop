package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.repository.ProductRepository;
import com.group3.torikago.Torikago.Shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductImplement implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductImplement(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<ProductDTO> findAllProducts() {
        List<Product> birdCages=productRepository.findAll();
        return birdCages.stream().map((product)->mapToProductDTO(product)).collect(Collectors.toList());
    }

    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO productDTO=ProductDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .image(product.getImage())
                .status(product.getStatus())
                .unitPrice(product.getUnitPrice())
                .unitsOnOrder(product.getUnitsOnOrder())
                .unitsInStock(product.getUnitsInStock())
                .accessoryDetail(product.getAccessoryDetail())
                .birdCageDetail(product.getBirdCageDetail())
                .build();
        return productDTO;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }



    @Override
    public void updateProduct(ProductDTO productDTO) {
        Product product=mapToProduct(productDTO);
        productRepository.save(product);
    }



    @Override
    public ProductDTO findProductById(Long productId) {
        Product product = productRepository.findById(productId).get();
        return mapToProductDTO(product);
    }


    private Product mapToProduct(ProductDTO product) {
        Product productDTO =Product.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .image(product.getImage())
                .status(product.getStatus())
                .unitPrice(product.getUnitPrice())
                .unitsOnOrder(product.getUnitsOnOrder())
                .unitsInStock(product.getUnitsInStock())
                .accessoryDetail(product.getAccessoryDetail())
                .birdCageDetail(product.getBirdCageDetail())
                .build();
        return productDTO;
    }
}
