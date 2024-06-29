package com.example.farm_management.controller;


import com.example.farm_management.pojo.Clock;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.ClockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clock")
public class ClockController {
    @Autowired
    ClockService clockService;

    @GetMapping
    public Result list(String eid, Date date, Time come, Time leave){
        log.info("查询签到信息");
        List<Clock> list = clockService.list(eid, date, come, leave);
        return Result.success(list);
    }

    @PostMapping
    public Result add(@RequestBody Clock clock){
        log.info("新增签到信息：{}",clock);
        clockService.add(clock);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<String> ids){
        log.info("批量删除操作, ids:{}",ids);
        clockService.delete(ids);
        return Result.success();
    }

    // 签到信息更新的时候需要指定eid和date
    // 否则的话可能会进行多个数据记录同时更新
    @PutMapping
    public Result update(@RequestBody Clock clock){
        log.info("更新签到信息：{}",clock);
        clockService.update(clock);
        return Result.success();
    }

}
