package com.example.farm_management.controller;


import com.example.farm_management.pojo.Farm;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.FarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/farm")
public class FarmController {

    @Autowired
    FarmService farmService;

    @GetMapping
    public Result list(String fname, String fid, String posinfo, String cname, String cid){
        log.info("查询农田信息");
        List<Farm> farmList = farmService.list(fname, fid, posinfo, cname, cid);
        return Result.success(farmList);
    }

    @PostMapping
    public  Result add(@RequestBody Farm farm){
        log.info("新增农田信息：{}",farm);
        farmService.add(farm);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<String> ids){
        log.info("批量删除操作, ids:{}",ids);
        farmService.delete(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Farm farm){
        log.info("更新农田信息：{}",farm);
        farmService.update(farm);
        return Result.success();
    }
}
