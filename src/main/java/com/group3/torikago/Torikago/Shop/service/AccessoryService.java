package com.group3.torikago.Torikago.Shop.service;

import com.group3.torikago.Torikago.Shop.dto.AccessoryDTO;
import com.group3.torikago.Torikago.Shop.dto.ProductDTO;
import com.group3.torikago.Torikago.Shop.model.AccessoryDetail;

import java.util.List;

public interface AccessoryService {
    AccessoryDetail saveAccessory(AccessoryDTO accessoryDTO, ProductDTO productDTO);

    AccessoryDetail updateAccessory(AccessoryDTO accessoryDTO, ProductDTO productDTO);
    
    AccessoryDetail findAccessoryById(Long accessoryID);
}
