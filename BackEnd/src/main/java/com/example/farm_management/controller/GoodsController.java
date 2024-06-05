package com.example.farm_management.controller;


import com.example.farm_management.pojo.Goods;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @GetMapping
    public Result list(String gid, String gname){
        log.info("查询商品信息");
        List<Goods> goodsList = goodsService.list(gid, gname);
        return Result.success(goodsList);
    }

    @PostMapping
    public  Result add(@RequestBody Goods goods){
        log.info("新增商品信息, ids：{}",goods);
        goodsService.add(goods);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<String> ids){
        log.info("批量删除操作：{}", ids);
        goodsService.delete(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Goods goods){
        log.info("更新商品信息：{}", goods);
        goodsService.update(goods);
        return Result.success();
    }
}
