package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.dto.AccessoryDTO;
import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.AccessoryDetail;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.repository.AccessoryRepository;
import com.group3.torikago.Torikago.Shop.service.AccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccessoryImplement implements AccessoryService {
    private AccessoryRepository accessoryRepository;
    @Autowired
    public AccessoryImplement(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }


    @Override
    public AccessoryDetail saveAccessory(AccessoryDTO accessoryDTO,ProductDTO productDTO) {
        AccessoryDetail result = mapToAccessory(accessoryDTO, productDTO);
        accessoryRepository.save(result);
        return result;
    }

    @Override
    public AccessoryDetail updateAccessory(AccessoryDTO accessoryDTO, ProductDTO productDTO) {
        AccessoryDetail accessoryDetail=mapToAccessory(accessoryDTO, productDTO);
        return accessoryRepository.save(accessoryDetail);
    }

    @Override
    public AccessoryDetail findAccessoryById(Long accessoryID) {
        AccessoryDetail accessoryDetail = accessoryRepository.findByAccessory_Id(accessoryID);
        return accessoryDetail;
    }

    private AccessoryDetail mapToAccessory(AccessoryDTO accessoryDTO, ProductDTO productDTO) {
        ProductImplement productImplement = new ProductImplement();
        Product product = productImplement.mapToProduct(productDTO);
        AccessoryDetail accessoryDetail=AccessoryDetail.builder()
                .id(accessoryDTO.getId())
                .accessory(product)
                .accessoryType(accessoryDTO.getAccessoryType())
                .description(accessoryDTO.getDescription())
                .build();
        return accessoryDetail;
    }

    private AccessoryDTO mapToAccessoryDTO(AccessoryDetail accessoryDetail) {
        AccessoryDTO accessoryDTO = AccessoryDTO.builder()
                .id(accessoryDetail.getId())
                .accessory(accessoryDetail.getAccessory())
                .accessoryType(accessoryDetail.getAccessoryType())
                .description(accessoryDetail.getDescription())
                .build();
        return accessoryDTO;
    }
}
