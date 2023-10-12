package com.group3.torikago.Torikago.Shop.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController {
    @GetMapping("/manager")
    @RolesAllowed({"MANAGER"})
    public String managerPage() {
        return "manager-dashboard";
    }
}
