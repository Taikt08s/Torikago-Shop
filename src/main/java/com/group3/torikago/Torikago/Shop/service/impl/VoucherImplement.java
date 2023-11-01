package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.dto.VoucherDTO;
import com.group3.torikago.Torikago.Shop.model.Voucher;
import com.group3.torikago.Torikago.Shop.repository.VoucherRepository;
import com.group3.torikago.Torikago.Shop.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class VoucherImplement implements VoucherService {
    private final VoucherRepository voucherRepository;
    @Autowired
    public VoucherImplement(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public List<VoucherDTO> findAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream().map((voucher) -> mapToVoucherDTO(voucher)).collect(Collectors.toList());
    }

    @Override
    public Voucher saveVoucher(VoucherDTO voucherDTO) {
        Voucher voucher=mapToVoucher(voucherDTO);
        return voucherRepository.save(voucher);
    }

    @Override
    public VoucherDTO findById(Long id) {
        Voucher voucher = voucherRepository.findById(id).get();
        return mapToVoucherDTO(voucher);
    }

    @Override
    public void updateVoucher(VoucherDTO voucherDTO) {
        Voucher voucher = mapToVoucher(voucherDTO);
        voucherRepository.save(voucher);
    }

    @Override
    public void deleteVoucher(Long id) {
        voucherRepository.deleteById(id);
    }

    private Voucher mapToVoucher(VoucherDTO voucherDTO) {
        Voucher voucher =Voucher.builder()
                .id(voucherDTO.getId())
                .voucherValue(voucherDTO.getVoucherValue())
                .voucherName(voucherDTO.getVoucherName())
                .createdTime(voucherDTO.getCreatedTime())
                .expiredTime(voucherDTO.getExpiredTime())
                .status(voucherDTO.getStatus())

                .build();
        return voucher;
    }

    private VoucherDTO mapToVoucherDTO(Voucher voucher) {
        VoucherDTO voucherDTO= VoucherDTO.builder()
                .id(voucher.getId())
                .voucherValue(voucher.getVoucherValue())
                .voucherName(voucher.getVoucherName())
                .createdTime(voucher.getCreatedTime())
                .expiredTime(voucher.getExpiredTime())
                .status(voucher.getStatus())

                .build();
        return voucherDTO;
    }
}
