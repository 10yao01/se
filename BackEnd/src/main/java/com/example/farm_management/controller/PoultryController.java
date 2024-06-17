package com.example.farm_management.controller;

import com.example.farm_management.pojo.Poultry;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.PoultryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/poultry")
public class PoultryController {


    @Autowired
    PoultryService poultryService;

    @GetMapping
    public Result list(String pid,
                       String pname,
                       String pinfo,
                       Integer feedinterval){
        log.info("查询家禽信息");
        List<Poultry> poultryList = poultryService.list(pid, pname, pinfo, feedinterval);
        return Result.success(poultryList);
    }

    @PostMapping
    public  Result add(@RequestBody Poultry poultry){
        log.info("新增家禽信息：{}",poultry);
        poultryService.add(poultry);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<String> ids){
        log.info("批量删除操作, ids:{}",ids);
        poultryService.delete(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Poultry poultry){
        log.info("更新家禽信息：{}",poultry);
        poultryService.update(poultry);
        return Result.success();
    }
}
