package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.model.Order;
import com.group3.torikago.Torikago.Shop.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import org.springframework.data.domain.Page;

public interface OrderService {
    
    Order saveOrderVNPay(User user, String orderValue);

    Order saveOrderCod(User user, String orderValue, String shippingFee);

    List<Order> listOrders(User user);

    Page<Order> findPaginatedOrders(int pageNumber, int pageSize, String sortField, String sortDir, String keyword);

    int totalPendingOrders();
    
    int totalShippingOrders();
    
    int totalDeliveredOrders();
    
    int totalCancelledOrders();
    
    void editOrderStatus(Long orderId, String status);

    Order findByOrderId(Long id);
}
