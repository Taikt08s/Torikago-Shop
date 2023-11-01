package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.dto.VoucherDTO;
import com.group3.torikago.Torikago.Shop.model.Voucher;

import java.util.List;

public interface VoucherService {
    List<VoucherDTO> findAllVouchers();

    Voucher saveVoucher(VoucherDTO voucherDTO);

    VoucherDTO findById(Long id);



    void updateVoucher(VoucherDTO voucher);

    void deleteVoucher(Long id);
}
