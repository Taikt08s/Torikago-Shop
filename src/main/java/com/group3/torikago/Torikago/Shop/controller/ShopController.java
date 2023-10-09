package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.service.ShoppingProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ShopController {
    private ShoppingProductService shoppingProductService;

    @Autowired
    public ShopController(ShoppingProductService shoppingProductService) {
        this.shoppingProductService = shoppingProductService;
    }

    @GetMapping(value = {"/torikago", "/"})
    public String shoppingPageListProducts(Model model,
                                           @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                           @RequestParam(name = "pageSize", defaultValue = "16") int pageSize,
                                           @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                                           @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {
        // Use these parameters to fetch a paginated and sorted list of products
        Page<Product> shoppingPage = shoppingProductService.findPaginatedShoppingProducts(pageNumber, pageSize, sortField, sortDir);
        model.addAttribute("products", shoppingPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        return "shopping-page";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "403";
    }

    @GetMapping("/torikago/product/{id}")
    public String productDetails(@PathVariable("id") Long id, Model model) {
        Product product = shoppingProductService.findProductById(id);
        model.addAttribute("product", product);
        return "shopping-product-detail";
    }
    @GetMapping("/torikago/search")
    public String searchShoppingProducts(@RequestParam(value = "query") String query, Model model) {
        List<Product> products = shoppingProductService.searchProducts(query);
        model.addAttribute("products", products);
        return "shopping-page";
//        return "shopping-product-search-list";
    }
}
