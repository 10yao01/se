package com.example.farm_management.controller;

import com.example.farm_management.pojo.Crop;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.CropService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/crop")
public class CropController {

    @Autowired
    CropService cropService;

    @GetMapping
    public Result list(String cid,
                       String cname,
                       String cinfo,
                       Integer wtime,
                       Integer ftime){
        log.info("查询农田信息");
        List<Crop> cropList = cropService.list(cid, cname, cinfo, wtime, ftime);
        return Result.success(cropList);
    }

    @PostMapping
    public  Result add(@RequestBody Crop crop){
        log.info("新增作物信息：{}",crop);
        cropService.add(crop);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<String> ids){
        log.info("批量删除操作, ids:{}",ids);
        cropService.delete(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Crop crop){
        log.info("更新作物信息：{}",crop);
        cropService.update(crop);
        return Result.success();
    }

}
