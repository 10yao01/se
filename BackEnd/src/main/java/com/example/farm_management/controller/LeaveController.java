package com.example.farm_management.controller;


import com.example.farm_management.pojo.Leave;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.LeaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/leave")
public class LeaveController {
    @Autowired
    LeaveService leaveService;

    @GetMapping
    public Result list(String eid, Date startdate, Date backdate, Integer isdealed){
        log.info("查询请假信息");
        List<Leave> leavelists = leaveService.list(eid, startdate, backdate, isdealed);
        return Result.success(leavelists);
    }

    @PostMapping
    public Result add(@RequestBody Leave leave){
        log.info("新增请假信息：{}", leave);
        leaveService.add(leave);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<String> ids){
        log.info("批量删除操作, ids:{}",ids);
        leaveService.delete(ids);
        return Result.success();
    }

    // 请假信息更新的时候需要指定eid和startdate
    // 否则的话可能会进行多个数据记录同时更新
    @PutMapping
    public Result update(@RequestBody Leave leave){
        log.info("更新请假信息：{}", leave);
        leaveService.update(leave);
        return Result.success();
    }
}
