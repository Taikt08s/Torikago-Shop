package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.dto.VoucherDTO;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.model.Voucher;
import com.group3.torikago.Torikago.Shop.repository.VoucherRepository;
import com.group3.torikago.Torikago.Shop.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<Voucher> findAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers;
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

    @Override
    public Page<VoucherDTO> findPaginatedVouchers(int pageNumber, int pageSize, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        if(keyword!=null){
            Page<Voucher> productsPage =  voucherRepository.findAll(pageable,keyword);
            return productsPage.map(this::mapToVoucherDTO);
        }
        Page<Voucher> productsPage = voucherRepository.findAll(pageable);
        return productsPage.map(this::mapToVoucherDTO);
    }

    private Voucher mapToVoucher(VoucherDTO voucherDTO) {
        Voucher voucher =Voucher.builder()
                .id(voucherDTO.getId())
                .voucherValue(voucherDTO.getVoucherValue())
                .voucherName(voucherDTO.getVoucherName())
                .createdTime(voucherDTO.getCreatedTime())
                .expiredTime(voucherDTO.getExpiredTime())
                .status(voucherDTO.getStatus())
                .maxValue((voucherDTO.getMaxValue()))
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
                .maxValue((voucher.getMaxValue()))
                .build();
        return voucherDTO;
    }
}
