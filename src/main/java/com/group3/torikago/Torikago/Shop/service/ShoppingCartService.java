package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.User;
import java.util.List;

public interface ShoppingCartService {
    List<CartItems> listCartItems(User user);
    int addProduct(Long productId, int quantity, User user);
    void updateQuantity(Long productId, int quantity, User user);
    void removeProduct(Long productId, User user);
}
