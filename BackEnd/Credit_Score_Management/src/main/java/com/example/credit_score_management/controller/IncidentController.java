package com.example.credit_score_management.controller;

import com.example.credit_score_management.pojo.Incident;
import com.example.credit_score_management.pojo.Result;
import com.example.credit_score_management.service.IncidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/incident")
public class IncidentController {

    @Autowired
    IncidentService incidentService;

    @GetMapping
    public Result list(String title, String name, Integer classes){
        log.info("查询事件信息");
        List<Incident> incidentList = incidentService.list(title,name,classes);
        return Result.success(incidentList);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("根据id删除事件：{}",id);
        incidentService.delete(id);
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody Incident incident){
        log.info("新增事件：{}",incident);
        incidentService.add(incident);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result updateIsdeal(@PathVariable Integer id) {
        log.info("处理事件");
        incidentService.updateIsdeal(id);
        return Result.success();
    }
}
