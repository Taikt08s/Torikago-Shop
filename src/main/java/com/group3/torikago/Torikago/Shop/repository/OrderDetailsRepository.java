
package com.group3.torikago.Torikago.Shop.repository;

import com.group3.torikago.Torikago.Shop.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {
    
}
