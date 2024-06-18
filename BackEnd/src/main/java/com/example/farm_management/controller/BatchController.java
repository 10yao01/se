package com.example.farm_management.controller;


import com.example.farm_management.pojo.Batch;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.BatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    BatchService batchService;

    @GetMapping
    public Result list(String bid,
                       Date bdate,
                       String originid,
                       String gid,
                       String gname,
                       Integer isdealed,
                       Integer amount){
        log.info("查询批次信息");
        List<Batch> batchList = batchService.list(bid, bdate, originid, gid, gname, isdealed, amount);
        return Result.success(batchList);
    }

    @PostMapping
    public Result add(@RequestBody Batch batch){
        log.info("新增批次信息：{}", batch);
        batchService.add(batch);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<String> ids){
        log.info("批量删除操作, ids:{}",ids);
        batchService.delete(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Batch batch){
        log.info("更新批次信息：{}",batch);
        batchService.update(batch);
        return Result.success();
    }

}
