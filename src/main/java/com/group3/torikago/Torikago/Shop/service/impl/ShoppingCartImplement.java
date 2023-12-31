package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.repository.CartItemRepository;
import com.group3.torikago.Torikago.Shop.repository.CustomizedBirdCageRepository;
import com.group3.torikago.Torikago.Shop.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.group3.torikago.Torikago.Shop.service.ShoppingCartService;

@Service
public class ShoppingCartImplement implements ShoppingCartService{
    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;

    private CustomizedBirdCageRepository customizedBirdCageRepository;
    
    @Autowired
    public ShoppingCartImplement(CartItemRepository cartItemRepository, ProductRepository productRepository, CustomizedBirdCageRepository customizedBirdCageRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
         this.customizedBirdCageRepository = customizedBirdCageRepository;
    }

    @Override
    public List<CartItems> listCartItems(User user) {
        return cartItemRepository.findByUserId(user);
    }

    @Override
    public List<CartItems> listCart(User user) {
        return cartItemRepository.findByUserIdAndCustomizedProductId(user.getId());
    }

    @Override
    public int addProduct(Long productId, int quantity, User user) {
        int addedQuantity = quantity;
        Product product = productRepository.findById(productId).get();
        CartItems cartItem = cartItemRepository.findByUserIdAndProductId(user, product);
        if (cartItem != null) {
            addedQuantity = cartItem.getQuantity() + quantity;
            if (product.getUnitsInStock()< addedQuantity) {
                return 0;
            } else {
                cartItem.setQuantity(addedQuantity);
            }
        } else {
            cartItem = new CartItems();
            cartItem.setUserId(user);
            cartItem.setQuantity(quantity);
            cartItem.setProductId(product);
        }

        cartItemRepository.save(cartItem);
        return addedQuantity;
    }

    @Override
    public void updateQuantity(Long productId, int quantity, User user) {
        cartItemRepository.updateQuantity(quantity, productId, user.getId());
    }

    @Override
    public void removeProduct(Long productId, User user) {
        Product product = productRepository.findById(productId).get();
        if(product.getCustomizedBirdCage() != null ){
            product.getCustomizedBirdCage().setCartStatus(false);
            customizedBirdCageRepository.save(product.getCustomizedBirdCage());
            return;
        }
        cartItemRepository.deleteByUserIdAndProductId(user.getId(), productId);
    }
    
}
