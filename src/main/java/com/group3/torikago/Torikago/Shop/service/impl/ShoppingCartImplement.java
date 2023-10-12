package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.repository.CartItemRepository;
import com.group3.torikago.Torikago.Shop.service.ShoppingCartServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartImplement implements ShoppingCartServices{
    private CartItemRepository cartItemRepository;
    
    @Autowired
    public ShoppingCartImplement(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<CartItems> listCartItems(User user) {
         return cartItemRepository.findByUserId(user);
    }
    
}
