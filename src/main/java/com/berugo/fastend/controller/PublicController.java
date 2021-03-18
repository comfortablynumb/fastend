package com.berugo.fastend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {
    @GetMapping(path = "/public")
    public String index() {
        return "public";
    }
}
