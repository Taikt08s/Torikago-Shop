package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.BirdCageDetail;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.repository.ProductRepository;
import com.group3.torikago.Torikago.Shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImplement implements ProductService {
    private ProductRepository productRepository;

    public ProductImplement() {
    }

    @Autowired
    public ProductImplement(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productType(product.getProductType())
                .mainImage(product.getMainImage())
                .extraImage1(product.getExtraImage1())
                .extraImage2(product.getExtraImage2())
                .extraImage3(product.getExtraImage3())
                .status(product.getStatus())
                .unitPrice(product.getUnitPrice())
                .unitWeight(product.getUnitWeight())
                .unitsOnOrder(product.getUnitsOnOrder())
                .unitsInStock(product.getUnitsInStock())
                .featureProduct(product.getFeatureProduct())
                .build();
        return productDTO;
    }

    @Override
    public Page<ProductDTO> findPaginatedProducts(int pageNumber, int pageSize, String sortField, String sortDir,String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        if(keyword!=null){
            Page<Product> productsPage =  productRepository.findAll(keyword,pageable);
            return productsPage.map(this::mapToProductDTO);
        }
        Page<Product> productsPage = productRepository.findAll(pageable);
        return productsPage.map(this::mapToProductDTO);

    }

    @Override
    public Page<Product> findCustomizedProducts(int pageNumber, int pageSize, String sortField, String sortDir,String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        if(keyword!=null){
            Page<Product> productsPage =  productRepository.findAllCustomizedProduct(pageable);
            return productsPage;
        }
        Page<Product> productsPage = productRepository.findAllCustomizedProduct(pageable);
        return productsPage;

    }

    @Override
    public Page<Product> findCustomizedProductsByUser(int pageNumber, int pageSize, String sortField, String sortDir, String keyword, Long id) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        if(keyword!=null){
            Page<Product> productsPage =  productRepository.findAllCustomizedProductByUser(pageable, id);
            return productsPage;
        }
        Page<Product> productsPage = productRepository.findAllCustomizedProductByUser(pageable, id);
        return productsPage;
    }

    @Override
    public Product findProductById(Long productId) {
        return productRepository.findById(productId).get();
    }

    public Product mapToProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .id(productDTO.getId())
                .productName(productDTO.getProductName())
                .productType(productDTO.getProductType())
                .mainImage(productDTO.getMainImage())
                .extraImage1(productDTO.getExtraImage1())
                .extraImage2(productDTO.getExtraImage2())
                .extraImage3(productDTO.getExtraImage3())
                .status(productDTO.getStatus())
                .unitPrice(productDTO.getUnitPrice())
                .unitWeight(productDTO.getUnitWeight())
                .unitsOnOrder(productDTO.getUnitsOnOrder())
                .unitsInStock(productDTO.getUnitsInStock())
                .featureProduct(productDTO.getFeatureProduct())
                .build();
        return product;
    }

}
