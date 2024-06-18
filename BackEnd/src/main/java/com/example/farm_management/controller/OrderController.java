package com.example.farm_management.controller;


import com.example.farm_management.pojo.Order;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    OrderService orderService;

    @GetMapping
    public Result list(String oid,
                       String customerid,
                       String gid,
                       String gname,
                       Integer amount,
                       Integer score){
        log.info("查询订单信息");
        List<Order> orderList = orderService.list(oid, customerid, gid, gname, amount, score);
        return Result.success(orderList);
    }

    @PostMapping
    public  Result add(@RequestBody Order order){
        log.info("新增订单信息：{}",order);
        orderService.add(order);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<String> ids){
        log.info("批量删除操作, ids:{}",ids);
        orderService.delete(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Order order){
        log.info("更新订单信息：{}",order);
        orderService.update(order);
        return Result.success();
    }

}
