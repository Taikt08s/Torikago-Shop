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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    public Page<Product> findPaginatedShoppingProductsByPriceRangeAndType(int pageNumber, int pageSize, String sortField, String sortDir, String type, Double priceFrom, Double priceTo) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        if (priceFrom != null || priceTo != null || type != null) {
                return shoppingProductsRepository.findAllByPriceRangeAndType(priceFrom, priceTo, type, pageable);
        }

        return shoppingProductsRepository.findAll(0, pageable);
    }
    @Override
    public List<Product> getRandomSimilarBirdCagesProducts(Long id) {
        Product baseProduct = shoppingProductsRepository.findById(id).orElse(null);

        if (baseProduct != null) {
            List<Product> allProductsExceptBase = shoppingProductsRepository.findAllBirdCagesByIdNot(id);

            // Shuffle the list randomly
            List<Product> randomProducts = shuffleList(allProductsExceptBase);

            int numberOfSimilarProducts = 10;
            List<Product> similarProducts = randomProducts.stream()
                    .limit(numberOfSimilarProducts)
                    .collect(Collectors.toList());

            return similarProducts;
        }

        return null;
    }

    @Override
    public List<Product> getRandomSimilarAccessoriesProducts(Long id) {
        Product baseProduct = shoppingProductsRepository.findById(id).orElse(null);

        if (baseProduct != null) {
            List<Product> allProductsExceptBase = shoppingProductsRepository.findAllAccessoriesByIdNot(id);

            // Shuffle the list randomly
            List<Product> randomProducts = shuffleList(allProductsExceptBase);

            int numberOfSimilarProducts = 10;
            List<Product> similarProducts = randomProducts.stream()
                    .limit(numberOfSimilarProducts)
                    .collect(Collectors.toList());

            return similarProducts;
        }

        return null;
    }

    //fisher-yates algorithm
    private <T> List<T> shuffleList(List<T> list) {
        List<T> shuffledList = new ArrayList<>(list);
        Random rand = new Random();

        for (int i = shuffledList.size() - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);

            T temp = shuffledList.get(index);
            shuffledList.set(index, shuffledList.get(i));
            shuffledList.set(i, temp);
        }

        return shuffledList;
    }
}
