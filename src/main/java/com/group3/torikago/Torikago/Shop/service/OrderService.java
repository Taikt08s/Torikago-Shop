package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.model.Order;
import com.group3.torikago.Torikago.Shop.model.User;
import java.util.List;

public interface OrderService {
    void saveOrder(User user, String orderValue);
    List<Order> listOrders(User user);
}
