package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.model.Order;
import com.group3.torikago.Torikago.Shop.model.OrderDetails;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.repository.OrderDetailsRepository;
import com.group3.torikago.Torikago.Shop.repository.OrderRepository;
import com.group3.torikago.Torikago.Shop.repository.UserRepository;
import com.group3.torikago.Torikago.Shop.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class DashBoardImplement implements DashBoardService {

    private UserRepository userRepository;

    private OrderRepository orderRepository;

    private OrderDetailsRepository orderDetailsRepository;
    public DashBoardImplement(){}

    @Autowired
    public DashBoardImplement(UserRepository userRepository, OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }
    @Override
    public double Revenue() {
        double result = 0;
        List<Order> list = orderRepository.findAll();

        for(Order order : list){
            if(order.getStatus().equals("Delivered")){
                result += order.getOrderValue();
            }
        }
        return result;
    }

    @Override
    public int NumberOfBestSeller(){
        Product product1 = null;
        int max = 0;
        HashMap<Product, Integer> myHashMap = new HashMap<Product, Integer>();
        List<Order> orderList= orderRepository.findAll();
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        for(Order order : orderList){
            if(order.getStatus().equals("Delivered")){
                if(order.getOrderDate().getMonth() == LocalDateTime.now().getMonth() &&
                        order.getOrderDate().getYear() == LocalDateTime.now().getYear()){
                    for(OrderDetails orderDetails : orderDetailsList){
                        if(orderDetails.getOrder().getId() == order.getId()){
                            boolean flag = false;
                            if(!myHashMap.containsKey(orderDetails.getProduct())){
                                myHashMap.put(orderDetails.getProduct(), orderDetails.getQuantity());
                            }else{
                                flag = true;
                                //update lại hashmap
                                myHashMap.put(orderDetails.getProduct(), myHashMap.get(orderDetails.getProduct()) + orderDetails.getQuantity());
                            }
                        }else{
                        }
                    }
                }
            }
        }
        for(Product product : myHashMap.keySet()){
            if(myHashMap.get(product) >= max){
                max = myHashMap.get(product);
                product1 = product;
            }
        }
        if(product1 == null){
            max = 0;
            return max;
        }
        return max;
    }
    @Override
    public Product BestSeller() {
        Product product1 = null;
        int max = 0;
        HashMap<Product, Integer> myHashMap = new HashMap<Product, Integer>();
        List<Order> orderList= orderRepository.findAll();
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        for(Order order : orderList){
            if(order.getStatus().equals("Delivered")){
                if(order.getOrderDate().getMonth() == LocalDateTime.now().getMonth() &&
                        order.getOrderDate().getYear() == LocalDateTime.now().getYear()){
                    for(OrderDetails orderDetails : orderDetailsList){
                        if(orderDetails.getOrder().getId() == order.getId()){
                            boolean flag = false;
                            if(!myHashMap.containsKey(orderDetails.getProduct())){
                                myHashMap.put(orderDetails.getProduct(), orderDetails.getQuantity());
                            }else{
                                flag = true;
                                //update lại hashmap
                                myHashMap.put(orderDetails.getProduct(), myHashMap.get(orderDetails.getProduct()) + orderDetails.getQuantity());
                            }
                        }else{
                        }
                    }
                }
            }
        }
        for(Product product : myHashMap.keySet()){
            if(myHashMap.get(product) >= max){
                max = myHashMap.get(product);
                product1 = product;
            }
        }
        if(product1 == null){
            product1 = new Product();
            product1.setProductName("");

            return product1;
        }
        return product1;
    }

    @Override
    public int NewUsers() {
        int count = 0;
        List<User> list = userRepository.findAll();
        for(User user : list){
            if(user.getCreateDate().getMonth() == LocalDateTime.now().getMonth() &&
                user.getCreateDate().getYear() == LocalDateTime.now().getYear()){
                count ++;
            }
        }
        return count;
    }

    @Override
    public int TotalOrders() {
        int count = 0;
        List<Order> list = orderRepository.findAll();
        for(Order order : list){
            if(order.getOrderDate().getMonth() == LocalDateTime.now().getMonth() &&
                    order.getOrderDate().getYear() == LocalDateTime.now().getYear()){
                count ++;
            }
        }
        return count;
    }
}
