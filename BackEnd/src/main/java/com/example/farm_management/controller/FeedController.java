package com.example.farm_management.controller;

import com.example.farm_management.pojo.FeedRecord;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    FeedService feedService;

    @GetMapping
    public Result list(String fid, String pid, Date ftime){
        log.info("查询喂食信息");
        List<FeedRecord> feedRecordListList = feedService.list(fid, pid, ftime);
        return Result.success(feedRecordListList);
    }

    @PostMapping
    public Result add(@RequestBody FeedRecord feedRecord){
        log.info("新增喂食信息：{}",feedRecord);
        feedService.add(feedRecord);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<String> ids){
        log.info("批量删除操作, ids:{}",ids);
        feedService.delete(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody FeedRecord feedRecord){
        log.info("更新农田信息：{}",feedRecord);
        feedService.update(feedRecord);
        return Result.success();
    }
}
