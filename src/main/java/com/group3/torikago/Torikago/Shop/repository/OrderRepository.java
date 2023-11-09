package com.group3.torikago.Torikago.Shop.repository;

import com.group3.torikago.Torikago.Shop.model.Order;
import com.group3.torikago.Torikago.Shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrder(User user);
    
    @Override
    Optional<Order> findById(Long id);

    @Query("SELECT o FROM Order o WHERE o.id LIKE CONCAT('%', :keyword, '%')")
    Page<Order> findAll(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.status LIKE 'Pending'")
    List<Order> findAllPendingOrders();
    
    @Query("SELECT o FROM Order o WHERE o.status LIKE 'Shipping'")
    List<Order> findAllShippingOrders();
    
    @Query("SELECT o FROM Order o WHERE o.status LIKE 'Delivered'")
    List<Order> findAllDeliveredOrders();
    
    @Query("SELECT o FROM Order o WHERE o.status LIKE 'Cancelled'")
    List<Order> findAllCancelledOrders();
}
