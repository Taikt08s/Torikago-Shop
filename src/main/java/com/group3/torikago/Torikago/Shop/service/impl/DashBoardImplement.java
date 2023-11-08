package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.model.BirdCageOrder;
import com.group3.torikago.Torikago.Shop.model.OrderDetails;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.repository.BirdCageOrderRepository;
import com.group3.torikago.Torikago.Shop.repository.OrderItemRepository;
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

    private BirdCageOrderRepository birdCageOrderRepository;

    private OrderItemRepository orderItemRepository;
    public DashBoardImplement(){}

    @Autowired
    public DashBoardImplement(UserRepository userRepository, BirdCageOrderRepository birdCageOrderRepository, OrderItemRepository orderItemRepository){
        this.birdCageOrderRepository = birdCageOrderRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
    }
    @Override
    public double Revenue() {
        double result = 0;
        List<BirdCageOrder> list = birdCageOrderRepository.findAll();
        for(BirdCageOrder birdCageOrder : list){
            result += birdCageOrder.getOrderValue();
        }
        return result;
    }

    @Override
    public Product BestSeller() {
        Product product1 = null;
        int max = 0;
        HashMap<Product, Integer> myHashMap = new HashMap<Product, Integer>();
        List<BirdCageOrder> orderList= birdCageOrderRepository.findAll();
        List<OrderDetails> orderDetailsList = orderItemRepository.findAll();
        for(BirdCageOrder birdCageOrder : orderList){
            if(birdCageOrder.getOrderDate().getMonth() == LocalDateTime.now().getMonth() &&
               birdCageOrder.getOrderDate().getYear() == LocalDateTime.now().getYear()){
                for(OrderDetails orderDetails : orderDetailsList){
                    if(orderDetails.getOrder().getOrderId() == birdCageOrder.getOrderId()){
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
        for(Product product : myHashMap.keySet()){
            if(myHashMap.get(product) >= max){
                max = myHashMap.get(product);
                product1 = product;
            }
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
        List<BirdCageOrder> list = birdCageOrderRepository.findAll();
        for(BirdCageOrder birdCageOrder : list){
            if(birdCageOrder.getOrderDate().getMonth() == LocalDateTime.now().getMonth() &&
                    birdCageOrder.getOrderDate().getYear() == LocalDateTime.now().getYear()){
                count ++;
            }
        }
        return count;
    }
}
