package com.group3.torikago.Torikago.Shop.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController {
//    implements ErrorController
//    @GetMapping("/error")
//    public String handleError(HttpServletRequest request){
//        int status = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//

//        if (status == HttpStatus.NOT_FOUND.value()) {
//            return "404";
//        }else if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//            return "404";
//        }
//        return "403";
//    }
}