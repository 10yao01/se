package com.example.farm_management.controller;

import com.example.farm_management.pojo.Area;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.AreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    AreaService areaService;

    @GetMapping
    public Result list(){
        log.info("查询全部项目地区");
        List<Area> areaList = areaService.list();
        return Result.success(areaList);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("根据id删除地区：{}",id);
        areaService.delete(id);
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody Area area){
        log.info("新增地区：{}",area);
        areaService.add(area);
        return Result.success();
    }

    @GetMapping("/{name}")
    public Result search(@PathVariable String name){
        log.info("查询地区：{}",name);
        List<Area> areaList = areaService.search(name);
        return Result.success(areaList);
    }
}
