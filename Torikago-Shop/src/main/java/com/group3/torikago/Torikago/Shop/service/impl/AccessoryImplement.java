package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.dto.AccessoryDTO;
import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.AccessoryDetail;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.repository.AccessoryRepository;
import com.group3.torikago.Torikago.Shop.service.AccessoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class AccessoryImplement implements AccessoryService {
    private AccessoryRepository accessoryRepository;
    @Autowired
    public AccessoryImplement(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    @Override
    public List<AccessoryDTO> findAllAccessories() {
        List<AccessoryDetail> accessoryDetails = accessoryRepository.findAll();
        return accessoryDetails.stream().map((accessoryDetail)->mapToAccessoryDTO(accessoryDetail)).collect(Collectors.toList());
    }

    @Override
    public AccessoryDetail saveAccessory(AccessoryDTO accessoryDTO,ProductDTO productDTO) {
        AccessoryDetail result = mapToAccessory(accessoryDTO, productDTO);
        accessoryRepository.save(result);
        return result;
    }

    @Override
    public void updateAccessory(AccessoryDTO accessoryDTO) {
        AccessoryDetail accessoryDetail=mapToAccessory(accessoryDTO);
        accessoryRepository.save(accessoryDetail);
    }

    @Override
    public void deleteAccessory(AccessoryDTO accessoryDTO) {
        AccessoryDetail accessoryDetail=mapToAccessory(accessoryDTO);
        accessoryRepository.delete(accessoryDetail);
    }

    @Override
    public AccessoryDTO findAccessoryById(Long accessoryID) {
        AccessoryDetail accessoryDetail = accessoryRepository.findById(accessoryID).get();
        return mapToAccessoryDTO(accessoryDetail);
    }

    private AccessoryDetail mapToAccessory(AccessoryDTO accessoryDTO, ProductDTO productDTO) {
        ProductImplement productImplement = new ProductImplement();
        Product product = productImplement.mapToProduct(productDTO);
        AccessoryDetail accessoryDetail=AccessoryDetail.builder()
                .accessory(product)
                .accessoryType(accessoryDTO.getAccessoryType())
                .description(accessoryDTO.getDescription())
                .build();
        return accessoryDetail;
    }
    private AccessoryDetail mapToAccessory(AccessoryDTO accessoryDTO) {
        AccessoryDetail accessoryDetail=AccessoryDetail.builder()
                .accessory(accessoryDTO.getAccessory())
                .accessoryType(accessoryDTO.getAccessoryType())
                .description(accessoryDTO.getDescription())
                .build();
        return accessoryDetail;
    }

    private AccessoryDTO mapToAccessoryDTO(AccessoryDetail accessoryDetail) {
        AccessoryDTO accessoryDTO = AccessoryDTO.builder()
                .accessory(accessoryDetail.getAccessory())
                .accessoryType(accessoryDetail.getAccessoryType())
                .description(accessoryDetail.getDescription())
                .build();
        return accessoryDTO;
    }
}
