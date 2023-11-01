/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.Order;
import com.group3.torikago.Torikago.Shop.model.OrderDetails;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.repository.CartItemRepository;
import com.group3.torikago.Torikago.Shop.repository.OrderDetailsRepository;
import com.group3.torikago.Torikago.Shop.repository.OrderRepository;
import com.group3.torikago.Torikago.Shop.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderImplement implements OrderService{
    private OrderRepository orderRepository;
    private CartItemRepository cartItemRepository;
    private OrderDetailsRepository orderDetailsRepository;
            
    @Autowired
    public OrderImplement(OrderRepository orderRepository, CartItemRepository cartItemRepository,
            OrderDetailsRepository orderDetailsRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    public void saveOrder(User user, String orderValue) {
        Order newOrder = new Order();
        List<CartItems> listItems = cartItemRepository.findByUserId(user);
        newOrder.setOrderValue(Double.parseDouble(orderValue)/100);
        newOrder.setUserOrder(user);
        newOrder.setShippedAddress(user.getAddress());
        newOrder.setStatus("pending");
        newOrder.setPaymentMethod("VNPay");
        orderRepository.save(newOrder);
        for (CartItems listItem : listItems) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(newOrder);
            orderDetails.setProduct(listItem.getProductId());
            orderDetails.setQuantity(listItem.getQuantity());
            orderDetails.setUnitPrice(listItem.getProductId().getUnitPrice());
            orderDetailsRepository.save(orderDetails);
        }
    }  
}
