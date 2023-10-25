package com.group3.torikago.Torikago.Shop.repository;

import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems,Long>  {
    
    public List<CartItems> findByUserId(User user);
    
    public CartItems findByUserIdAndProductId(User user, Product product);
    
    @Query("UPDATE CartItems c SET c.quantity = ?1 WHERE c.productId.id = ?2 AND c.userId.id = ?3")
    @Modifying
    @Transactional
    public void updateQuantity(int quantity, Long productId, Long userId);
    
    @Query("DELETE FROM CartItems c WHERE c.userId.id = ?1 AND c.productId.id = ?2")
    @Modifying
    @Transactional
    public void deleteByUserIdAndProductId(Long userId, Long productId);
}
