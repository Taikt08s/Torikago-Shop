package com.group3.torikago.Torikago.Shop.controller;

import com.group3.torikago.Torikago.Shop.model.CartItems;
import com.group3.torikago.Torikago.Shop.model.Product;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.service.ShoppingCartServices;
import com.group3.torikago.Torikago.Shop.service.ShoppingProductService;
import com.group3.torikago.Torikago.Shop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopController {
    private ShoppingProductService shoppingProductService;
    private ShoppingCartServices shoppingCartServices;
    private UserService userService;

    @Autowired
    public ShopController(ShoppingProductService shoppingProductService, ShoppingCartServices shoppingCartServices, UserService userService) {
        this.shoppingProductService = shoppingProductService;
        this.shoppingCartServices = shoppingCartServices;
        this.userService = userService;
    }

    @GetMapping(value = {"/torikago", "/"})
    public String shoppingPageListProducts(Model model,
                                           @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                           @RequestParam(name = "pageSize", defaultValue = "16") int pageSize,
                                           @RequestParam(name = "priceSort", defaultValue = "id") String sortField,
                                           @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                                           @RequestParam(name = "search", required = false) String search,
                                           @RequestParam(name = "priceFrom", required = false) Double priceFrom,
                                           @RequestParam(name = "priceTo", required = false) Double priceTo,
                                           @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails) {

        if ("lowPrice".equals(sortField)) {
            sortField = "unitPrice";
            sortDir = "asc";
        } else if ("highPrice".equals(sortField)) {
            sortField = "unitPrice";
            sortDir = "desc";
        }

        String userName = (myUserDetails != null) ? myUserDetails.getUsername() : null;
        List<CartItems> cartItems = new ArrayList<>();

        if (userName != null) {
            User user = userService.findByEmail(userName);
            cartItems = shoppingCartServices.listCartItems(user);
            model.addAttribute("cartItems", cartItems);
        } else {
            model.addAttribute("cartItems", new ArrayList<CartItems>());
        }

        Page<Product> shoppingPage;

        if (priceFrom != null || priceTo != null) {
            shoppingPage = shoppingProductService.findPaginatedShoppingProductsByPriceRange(
                    pageNumber, pageSize, sortField, sortDir, search, priceFrom, priceTo);
        } else {
            shoppingPage = shoppingProductService.findPaginatedShoppingProducts(
                    pageNumber, pageSize, sortField, sortDir, search);
        }

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
    public String productDetails(@PathVariable("id") Long id, Model model, HttpServletRequest request
            , @AuthenticationPrincipal org.springframework.security.core.userdetails.User myUserDetails) {
        Product product = shoppingProductService.findProductById(id);
        ArrayList<Product> comparisonList = (ArrayList<Product>) request.getSession().getAttribute("comparisonList");


        if (comparisonList == null) {
            comparisonList = new ArrayList<>();

            request.getSession().setAttribute("comparisonList", comparisonList);
        }
        String userName = (myUserDetails != null) ? myUserDetails.getUsername() : null;

        if (userName != null) {
            User user = userService.findByEmail(userName);
            List<CartItems> cartItems = shoppingCartServices.listCartItems(user);
            model.addAttribute("cartItems", cartItems);
        } else {
            model.addAttribute("cartItems", new ArrayList<CartItems>());
        }

        if (product.getProductType().equals("Bird Cage")) {
            List<Product> randomSimilarBirdCagesProducts = shoppingProductService.getRandomSimilarBirdCagesProducts(id);
            model.addAttribute("randomSimilarBirdCagesProducts", randomSimilarBirdCagesProducts);

            model.addAttribute("product", product);
            return "shopping-product-birdcage-detail";
        } else if (product.getProductType().equals("Accessory"))
            model.addAttribute("product", product);
        List<Product> randomSimilarAccessoriesProducts = shoppingProductService.getRandomSimilarAccessoriesProducts(id);
        model.addAttribute("randomSimilarAccessoriesProducts", randomSimilarAccessoriesProducts);

        return "shopping-product-accessory-detail";

    }

    @GetMapping("/torikago/product/compare/{id}")
    public String addToCompare(@PathVariable("id") Long id, HttpServletRequest request) {
        Product product = shoppingProductService.findProductById(id);
        ArrayList<Product> comparisonList = (ArrayList<Product>) request.getSession().getAttribute("comparisonList");
        boolean check = false;
        for (Product p : comparisonList) {
            if (p.getId() == product.getId()) {
                check = true;
                break;
            }
        }
        if (!check) {
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
    public String deleteCompareProduct(@PathVariable("id") Long id, HttpServletRequest request) {
        Product product = shoppingProductService.findProductById(id);
        ArrayList<Product> comparisonList = (ArrayList<Product>) request.getSession().getAttribute("comparisonList");
        comparisonList.removeIf(p -> p.getId() == product.getId());
        request.getSession().setAttribute("comparisonList", comparisonList);
        return "redirect:/torikago/product/compare";
    }
    @GetMapping("/compare/product/delete/modal/{id}")
    public String removeProductFromComparisonList(@PathVariable("id") Long id, HttpServletRequest request) {
        Product product = shoppingProductService.findProductById(id);
        ArrayList<Product> comparisonList = (ArrayList<Product>) request.getSession().getAttribute("comparisonList");
        comparisonList.removeIf(p -> p.getId() == product.getId());
        request.getSession().setAttribute("comparisonList", comparisonList);
        return "redirect:/torikago/product/{id}?openCompareModal=true";
    }
}
