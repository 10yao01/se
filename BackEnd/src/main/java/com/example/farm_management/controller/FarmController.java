package com.example.farm_management.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.text.SimpleDateFormat;


import com.example.farm_management.pojo.Farm;
import com.example.farm_management.pojo.FarmFeature;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.FarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/farm")
public class FarmController {

    @Autowired
    FarmService farmService;

    private List<FarmFeature> ConvertStringToFarmFeature(String farmStr) {
        List<FarmFeature> farmFeatureList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(farmStr);
            for (JsonNode node : rootNode) {
                String dateStr = node.get("date").asText();
                Integer moisture = node.get("moisture").asInt();
                Integer nutrient = node.get("nutrient").asInt();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);
                Date date = new Date(sdf.parse(dateStr).getTime());
                FarmFeature farmFeature = new FarmFeature(date, moisture, nutrient);
                farmFeatureList.add(farmFeature);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return farmFeatureList;
    }

    @GetMapping
    public Result list(String fname, String fid, String wid, String posinfo, String cname, String cid, Integer soiltype) {
        log.info("查询农田信息");
        List<Farm> farmList = farmService.list(fname, fid, wid, posinfo, cname, cid, soiltype);
        for (Farm farm : farmList) {
            Object farmFeatureList = farm.getStatistics(); // String类型
            farm.setStatistics(ConvertStringToFarmFeature((String) farmFeatureList));
        }
        return Result.success(farmList);
    }

    // 这里限制新增的话，不对土地特性进行新增
    // 土地特性只通过更改进行
    @PostMapping
    public Result add(@RequestBody Farm farm) {
        log.info("新增农田信息：{}", farm);
        farmService.add(farm);
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<String> ids) {
        log.info("批量删除操作, ids:{}", ids);
        farmService.delete(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Farm farm) {
        log.info("更新农田信息：{}", farm);

        System.out.println(farm.getStatistics().getClass());
        farmService.update(farm);
        return Result.success();
    }
}
