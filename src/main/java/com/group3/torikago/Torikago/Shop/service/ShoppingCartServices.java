package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.User;
import java.util.List;

public interface ShoppingCartServices {
    List<CartItems> listCartItems(User user);
}
