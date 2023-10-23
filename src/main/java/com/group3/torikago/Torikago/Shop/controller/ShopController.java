package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.service.ShoppingProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

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
                                           @RequestParam(name = "pageSize", defaultValue = "18") int pageSize,
                                           @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                                           @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                                           @RequestParam(name = "search", required = false) String search) {
        // Use these parameters to fetch a paginated and sorted list of products
        Page<Product> shoppingPage = shoppingProductService.findPaginatedShoppingProducts(pageNumber, pageSize, sortField, sortDir, search);
        model.addAttribute("products", shoppingPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("search", search);
        return "shopping-page";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "403";
    }

    @GetMapping("/torikago/product/{id}")
    public String productDetails(@PathVariable("id") Long id, Model model,HttpServletRequest request) {
        Product product = shoppingProductService.findProductById(id);
        ArrayList<Product> comparisonList = (ArrayList<Product>) request.getSession().getAttribute("comparisonList");
        if (comparisonList == null) {
            comparisonList = new ArrayList<>();

            request.getSession().setAttribute("comparisonList", comparisonList);
        }
        if(product.getProductType().equals("Bird Cage")){
        model.addAttribute("product", product);
        return "shopping-product-birdcage-detail";
    } else if (product.getProductType().equals("Accessory"))
            model.addAttribute("product", product);
        return "shopping-product-accessory-detail";
    }
    @GetMapping("/torikago/product/compare/{id}")
    public String addToCompare(@PathVariable("id") Long id, HttpServletRequest request) {

        Product product = shoppingProductService.findProductById(id);
        ArrayList<Product> comparisonList = (ArrayList<Product>) request.getSession().getAttribute("comparisonList");
            boolean check=false;
            for (Product p : comparisonList) {
                if (p.getId() == product.getId()) {
                    check = true;
                    break;
                }
            }
            if(!check) {
                int maxProducts = 3;

                if (comparisonList.size() < maxProducts) {
                    if (!comparisonList.contains(product)) {
                        comparisonList.add(product);
                    }
                } else {
                    comparisonList.remove(comparisonList.size() - 1);
                    comparisonList.add(product);
                }
            }
        request.getSession().setAttribute("comparisonList", comparisonList);
       return "redirect:/torikago/product/{id}?openCompareModal=true";
    }
    @GetMapping("/torikago/product/compare")
    public String showCompareList(Model model, HttpServletRequest request) {
        ArrayList<Product> comparisonList = (ArrayList<Product>) request.getSession().getAttribute("comparisonList");
        model.addAttribute("comparisonList", comparisonList);
        return "shopping-product-compare";
    }
    @GetMapping("/compare/product/delete/{id}")
    public String deleteCompareProduct(@PathVariable("id")Long id, HttpServletRequest request){
        Product product = shoppingProductService.findProductById(id);
        ArrayList<Product> comparisonList = (ArrayList<Product>) request.getSession().getAttribute("comparisonList");
        comparisonList.removeIf(p -> p.getId() == product.getId());
        request.getSession().setAttribute("comparisonList", comparisonList);
        return "shopping-product-compare";
    }
}
