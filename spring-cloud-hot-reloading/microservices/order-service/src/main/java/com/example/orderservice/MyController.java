package com.example.orderservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/config")
public class MyController {

    @Value("${db.password}")
    private String dbpassword;

    @Value("${db.name}")
    private String dbname;

    @GetMapping
    public String getConfigValue() {
        return "database password is: "+ dbpassword + ", and dbname is: "+ dbname;
    }
}