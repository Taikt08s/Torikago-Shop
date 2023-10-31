package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.User;
import java.util.List;

public interface ShoppingCartServices {
    List<CartItems> listCartItems(User user);
    List<CartItems> listCart(User user);
    int addProduct(Long productId, int quantity, User user);
    void updateQuantity(Long productId, int quantity, User user);
    void removeProduct(Long productId, User user);
}
