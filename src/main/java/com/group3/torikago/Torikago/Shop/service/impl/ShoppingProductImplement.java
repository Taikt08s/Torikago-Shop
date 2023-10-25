package com.group3.torikago.Torikago.Shop.service.impl;

import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.repository.ShoppingProductsRepository;
import com.group3.torikago.Torikago.Shop.service.ShoppingProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingProductImplement implements ShoppingProductService {
    private ShoppingProductsRepository shoppingProductsRepository;

    @Autowired
    public ShoppingProductImplement(ShoppingProductsRepository shoppingProductsRepository) {
        this.shoppingProductsRepository = shoppingProductsRepository;
    }

    @Override
    public Page<Product> findPaginatedShoppingProducts(int pageNumber, int pageSize, String sortField, String sortDir, String search) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        if (search != null && !search.isEmpty()) {
            return shoppingProductsRepository.findAll("%" + search + "%", pageable);
        }

        return shoppingProductsRepository.findAll(0, pageable);
    }

    @Override
    public Product findProductById(Long id) {
        return shoppingProductsRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Product> findPaginatedBirdCageProducts(int pageNumber, int pageSize, String sortField, String sortDir, String search) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        if (search != null && !search.isEmpty()) {
            return shoppingProductsRepository.findAll("%" + search + "%", pageable);
        }

        return shoppingProductsRepository.findAll(pageable);
    }
}
