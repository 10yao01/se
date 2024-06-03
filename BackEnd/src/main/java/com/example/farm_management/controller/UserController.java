package com.example.farm_management.controller;


import com.example.farm_management.pojo.Result;
import com.example.farm_management.pojo.User;
import com.example.farm_management.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public Result list(String uid, String name, Integer gender, String tel){
        log.info("查询用户信息");
        List<User> userList  = userService.list(uid, name, gender, tel);
        return Result.success(userList);
    }

    @PostMapping
    public Result add(@RequestBody User user){
        log.info("新增用户信息：{}", user);
        userService.add(user);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<String> ids){
        log.info("批量删除操作, ids:{}",ids);
        userService.delete(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody User user){
        log.info("更新用户信息：{}", user);
        userService.update(user);
        return Result.success();
    }

}
