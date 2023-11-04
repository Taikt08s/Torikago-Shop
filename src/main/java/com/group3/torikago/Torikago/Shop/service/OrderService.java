package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.model.Order;
import com.group3.torikago.Torikago.Shop.model.User;
import java.util.List;

public interface OrderService {
    void saveOrderVNPay(User user, String orderValue);
    void saveOrderCod(User user, String orderValue, String shippingFee);
    List<Order> listOrders(User user);
}
