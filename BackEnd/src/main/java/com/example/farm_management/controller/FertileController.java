package com.example.farm_management.controller;

import com.example.farm_management.pojo.FertileRecord;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.FertileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/fertile")
public class FertileController {
    @Autowired
    FertileService fertileService;

    @GetMapping
    public Result list(String fid, String farmid, Integer opid, Date optime){
        log.info("查询喂食信息");
        List<FertileRecord> feedRecordListList = fertileService.list(fid, farmid, opid, optime);
        return Result.success(feedRecordListList);
    }

    @PostMapping
    public Result add(@RequestBody FertileRecord fertileRecord){
        log.info("新增浇水灌溉信息：{}", fertileRecord);
        fertileService.add(fertileRecord);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<String> ids){
        log.info("批量删除操作, ids:{}",ids);
        fertileService.delete(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody  FertileRecord fertileRecord){
        log.info("更新浇水灌溉信息：{}",fertileRecord);
        fertileService.update(fertileRecord);
        return Result.success();
    }
}
