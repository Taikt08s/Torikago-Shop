package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.repository.CartItemRepository;
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
    
    @Autowired
    public ShoppingCartImplement(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CartItems> listCartItems(User user) {
         return cartItemRepository.findByUserId(user);
    }

    @Override
    public int addProduct(Long productId, int quantity, User user) {
        int addedQuantity = quantity;
        Product product = productRepository.findById(productId);
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
        cartItemRepository.deleteByUserIdAndProductId(user.getId(), productId);
    }
    
}
