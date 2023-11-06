package com.group3.torikago.Torikago.Shop.repository;

import com.group3.torikago.Torikago.Shop.model.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher,Long> {

    Optional<Voucher> findById(Long id);
    @Query("SELECT v FROM Voucher v WHERE v.voucherName LIKE %?1%")
    Page<Voucher> findAll(Pageable pageable, String keyword);
}
