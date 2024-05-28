package com.example.farm_management.controller;

import com.example.farm_management.pojo.Pasture;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.PastureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pasture")
public class PastureController {

    @Autowired
    PastureService pastureService;

    @GetMapping
    public Result list(String pname, String pid, String pos, String poultryid, String poultryname){
        log.info("查询养殖场信息");
        List<Pasture> pastureList = pastureService.list(pname, pid, pos, poultryid, poultryname);
        return  Result.success(pastureList);
    }

    @PostMapping
    public Result add(@RequestBody Pasture pasture){
        log.info("新增养殖场信息：{}",pasture);
        pastureService.add(pasture);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("批量删除操作：{}",ids);
        pastureService.delete(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Pasture pasture){
        log.info("更新养殖场信息：{}",pasture);
        pastureService.update(pasture);
        return Result.success();
    }
}
