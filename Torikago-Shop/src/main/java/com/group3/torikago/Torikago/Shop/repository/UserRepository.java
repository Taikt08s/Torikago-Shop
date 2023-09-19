package com.group3.torikago.Torikago.Shop.repository;


import com.group3.torikago.Torikago.Shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM users u WHERE u.userName = :userName")
    public User getUserByUsername(@Param("userName") String userName);
}
