package com.example.farm_management.controller;

import com.example.farm_management.pojo.Result;
import com.example.farm_management.pojo.User;
import com.example.farm_management.service.UserService;
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
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User user1 = userService.login(user);
        if (user1!=null){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",user1.getUid());
            claims.put("name",user1.getName());
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }
        return Result.error("错误！");
    }
}
