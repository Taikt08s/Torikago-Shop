package com.group3.torikago.Torikago.Shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopController {
    @GetMapping(value ={ "/torikago","/"})
    public String shoppingPage(){
        return "shopping-page";
    }
    @RequestMapping("/403")
    public String accessDenied() {
        return "403";
    }
}
