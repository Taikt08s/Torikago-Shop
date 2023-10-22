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


//    @Override
//    public List<ProductDTO> findAllProducts() {
//        List<Product> birdCages = productRepository.findAll();
//        return birdCages.stream().map((product) -> mapToProductDTO(product)).collect(Collectors.toList());
//    }

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
                .unitsOnOrder(product.getUnitsOnOrder())
                .unitsInStock(product.getUnitsInStock())
                .build();
        return productDTO;
    }


//    @Override
//    public Product saveProduct(ProductDTO productDTO) {
//        Product product = mapToProduct(productDTO);
//        return productRepository.save(product);
//    }


//    @Override
//    public void updateProduct(ProductDTO productDTO) {
////        Product product=mapToProduct(productDTO);
////        productRepository.save(product);
//    }


//    @Override
//    public void deleteProduct(Long productId) {
////        productRepository.deleteById(productId);
//    }


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
    public ProductDTO findProductById(Long productId) {

        return null;
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
                .unitsOnOrder(productDTO.getUnitsOnOrder())
                .unitsInStock(productDTO.getUnitsInStock())
                .build();
        return product;
    }
}
