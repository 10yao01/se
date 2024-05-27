package com.example.farm_management.controller;

import com.example.farm_management.pojo.Farmer;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.FarmerService;
import com.example.farm_management.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class LoginController {

    @Autowired
    private FarmerService farmerService;

    @PostMapping("/login")
    public Result login(@RequestBody Farmer farmer) {
        Farmer farmer1 = farmerService.login(farmer);
        if (farmer1!=null){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",farmer1.getFid());
            claims.put("name",farmer1.getName());
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }
        return Result.error("错误！");
    }
}
