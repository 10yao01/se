package com.example.farm_management.controller;

import com.example.farm_management.pojo.Farmer;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.FarmerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/farmer")
public class FarmerController {

    @Autowired
    FarmerService farmerService;

//    @GetMapping
//    public Result page(@RequestParam(defaultValue = "1") Integer page,
//                       @RequestParam(defaultValue = "10") Integer pageSize,
//                       String name, Short gender, Integer age,
//                       String image, String area, Integer score){
//        log.info("分页查询, 参数: {},{},{},{},{},{},{},{}",page,pageSize,name,gender,age,image,area,score);
//        //调用service分页查询
//        PageBean pageBean = farmerService.page(page,pageSize,name,gender,age,image,area,score);
//        return Result.success(pageBean);
//    }
    @GetMapping
    public Result list(String name, Short gender, String areaname){
        log.info("查询农户信息");
        List<Farmer> farmerList = farmerService.list(name,gender,areaname);
        return Result.success(farmerList);
    }

    @PostMapping
    public Result add(@RequestBody Farmer farmer){
        log.info("新增农民：",farmer);
        farmerService.add(farmer);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("批量删除操作, ids:{}",ids);
        farmerService.delete(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Farmer farmer){
        log.info("更新农民信息：{}",farmer);
        farmerService.update(farmer);
        return Result.success();
    }
}
