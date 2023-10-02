package com.group3.torikago.Torikago.Shop.repository;

import com.group3.torikago.Torikago.Shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUserName(String userName);

    User findFirstByUserName(String username);

    @Modifying
    @Query("UPDATE users u SET u.enabled = true WHERE u.id = :id")
    void enableById(@Param("id") Long id);

    @Query("SELECT u FROM users u WHERE u.verificationCode = :code")
    User findByVerificationCode(@Param("code") String code);

    User findByResetPasswordToken(String token);
}
