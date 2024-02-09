package com.example.catalog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/config")
public class MyController {

    @Value("${db.password}")
    private String dbpassword;

    @Value("${db.name}")
    private String dbname;

    @Value("${my.test.config}")
    private String myTestConfig;

    @GetMapping
    public String getConfigValue() {
        return "database password is: "+ dbpassword + ", and dbname is: "+ dbname + " and my test config is: "+ myTestConfig;
    }
}