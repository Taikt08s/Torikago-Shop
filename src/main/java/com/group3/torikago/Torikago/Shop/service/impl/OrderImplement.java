/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.Order;
import com.group3.torikago.Torikago.Shop.model.OrderDetails;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.model.Voucher;
import com.group3.torikago.Torikago.Shop.repository.CartItemRepository;
import com.group3.torikago.Torikago.Shop.repository.OrderDetailsRepository;
import com.group3.torikago.Torikago.Shop.repository.OrderRepository;
import com.group3.torikago.Torikago.Shop.repository.ProductRepository;
import com.group3.torikago.Torikago.Shop.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class OrderImplement implements OrderService{
    private OrderRepository orderRepository;
    private CartItemRepository cartItemRepository;
    private OrderDetailsRepository orderDetailsRepository;
    private ProductRepository productRepository;
            
    @Autowired
    public OrderImplement(OrderRepository orderRepository, CartItemRepository cartItemRepository,
            OrderDetailsRepository orderDetailsRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order saveOrderVNPay(User user, String orderValue) {
        Order newOrder = new Order();
        List<CartItems> listItems = cartItemRepository.findByUserId(user);
        newOrder.setOrderValue(Double.parseDouble(orderValue)/100);
        newOrder.setUserOrder(user);
        newOrder.setShippedAddress(user.getAddress());
        newOrder.setStatus("Pending");
        newOrder.setPaymentMethod("VNPay");
        double productPrice = 0;
        for (CartItems listItem : listItems) {
            productPrice += listItem.getSubtotal();
        }
        newOrder.setShippingFee(Double.parseDouble(orderValue)/100 - productPrice);
        Order order = orderRepository.save(newOrder);
        order.setOrderdetails(new ArrayList<OrderDetails>());
        for (CartItems listItem : listItems) {
            OrderDetails orderDetails = new OrderDetails();
            Product product = productRepository.findById(listItem.getProductId().getId()).get();
            product.setUnitsInStock(product.getUnitsInStock() - listItem.getQuantity());
            product.setUnitsOnOrder(product.getUnitsOnOrder() + listItem.getQuantity());
            orderDetails.setOrder(newOrder);
            orderDetails.setProduct(listItem.getProductId());
            orderDetails.setQuantity(listItem.getQuantity());
            orderDetails.setUnitPrice(listItem.getProductId().getUnitPrice());
            order.getOrderdetails().add(orderDetails);
            productRepository.save(product);
            orderDetailsRepository.save(orderDetails);
            cartItemRepository.delete(listItem);
        }
        return order;
    }

    @Override
    public Order saveOrderCod(User user, double orderValue, double shippingFee, Voucher voucher) {
        Order newOrder = new Order();
        List<CartItems> listItems = cartItemRepository.findByUserId(user);
        newOrder.setOrderValue(orderValue + shippingFee);
        newOrder.setUserOrder(user);
        newOrder.setShippedAddress(user.getAddress());
        newOrder.setStatus("Pending");
        newOrder.setVoucher(voucher);
        newOrder.setPaymentMethod("Cash on delivery");
        newOrder.setShippingFee(shippingFee);
        Order order = orderRepository.save(newOrder);
        order.setOrderdetails(new ArrayList<OrderDetails>());
        for (CartItems listItem : listItems) {
            OrderDetails orderDetails = new OrderDetails();
            Product product = productRepository.findById(listItem.getProductId().getId()).get();
            product.setUnitsInStock(product.getUnitsInStock() - listItem.getQuantity());
            product.setUnitsOnOrder(product.getUnitsOnOrder() + listItem.getQuantity());
            orderDetails.setOrder(newOrder);
            orderDetails.setProduct(listItem.getProductId());
            orderDetails.setQuantity(listItem.getQuantity());
            orderDetails.setUnitPrice(listItem.getProductId().getUnitPrice());
            order.getOrderdetails().add(orderDetails);
            productRepository.save(product);
            orderDetailsRepository.save(orderDetails);
            cartItemRepository.delete(listItem);
        }
        return order;
    }

    @Override
    public List<Order> listOrders(User user) {
        return orderRepository.findByUserOrder(user);
    }

    @Override
    public Page<Order> findPaginatedOrders(int pageNumber, int pageSize, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        if(keyword!=null){
            if (keyword.isEmpty()) {
                Page<Order> OrdersPage = orderRepository.findAll(pageable);
                return OrdersPage;
            } else {Page<Order> OrdersPage =  orderRepository.findAll(keyword,pageable);
            return OrdersPage;
            }
        }
        Page<Order> OrdersPage = orderRepository.findAll(pageable);
        return OrdersPage;
    }

    @Override
    public int totalPendingOrders() {
        return orderRepository.findAllPendingOrders().size();
    }

    @Override
    public int totalShippingOrders() {
        return orderRepository.findAllShippingOrders().size();
    }

    @Override
    public int totalDeliveredOrders() {
        return orderRepository.findAllDeliveredOrders().size();
    }

    @Override
    public int totalCancelledOrders() {
        return orderRepository.findAllCancelledOrders().size();
    }

    @Override
    public void editOrderStatus(Long orderId, String status) {
         Order order = orderRepository.findById(orderId).get();
         order.setStatus(status);
         orderRepository.save(order);
    }

    @Override
    public Order findByOrderId(Long id) {
        Order order = orderRepository.findById(id).get();
        return order;
    }
}
