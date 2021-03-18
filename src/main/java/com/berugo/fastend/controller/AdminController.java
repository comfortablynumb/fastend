package com.berugo.fastend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @GetMapping(path = "/admin")
    public String index() {
        return "private";
    }
}
