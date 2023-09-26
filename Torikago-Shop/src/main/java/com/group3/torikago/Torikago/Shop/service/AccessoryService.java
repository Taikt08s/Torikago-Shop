package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.dto.AccessoryDTO;
import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.AccessoryDetail;

import java.util.List;

public interface AccessoryService {
    List<AccessoryDTO> findAllAccessories();
    AccessoryDetail saveProduct(AccessoryDTO accessoryDTO, ProductDTO productDTO);

    void updateAccessory(AccessoryDTO accessoryDTO);

    void deleteAccessory(AccessoryDTO accessoryDTO);
    AccessoryDTO findAccessoryById(Long accessoryID);
}
