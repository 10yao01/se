package com.example.credit_score_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@ServletComponentScan
@SpringBootApplication
@CrossOrigin
public class CreditScoreManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditScoreManagementApplication.class, args);
    }

}
