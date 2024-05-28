package com.example.farm_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@ServletComponentScan
@SpringBootApplication
@CrossOrigin
public class FarmManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarmManagementApplication.class, args);
    }

}
