//package com.group3.torikago.Torikago.Shop;
//
//import com.group3.torikago.Torikago.Shop.model.Product;
//import com.group3.torikago.Torikago.Shop.repository.BirdCageRepository;
//import com.group3.torikago.Torikago.Shop.repository.ProductRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.annotation.Rollback;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(false)
//public class testCreateProduct {
//    @Autowired
//private TestEntityManager  entityManager;
//    @Autowired
//private ProductRepository productRepository;
//    @Autowired
//private BirdCageRepository birdCageRepository;
//
//    @Test
//    public void testCreateProduct(){
//        Product product = new Product();
//        product.setProductName("long chim go kien");
//        product.setProductType("vuong");
//        product.setImage("ko co");
//        Product savedProduct = productRepository.save(product);
//        Product existProduct =entityManager.find(Product.class, savedProduct.getProductId());
//    }
//}
