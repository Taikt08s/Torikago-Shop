package com.group3.torikago.Torikago.Shop.service;


import com.group3.torikago.Torikago.Shop.model.Product;

public interface DashBoardService {
    double Revenue();
    Product BestSeller();
    int NewUsers();
    int TotalOrders();
}
