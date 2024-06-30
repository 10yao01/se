package com.example.farm_management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.text.SimpleDateFormat;


import com.example.farm_management.pojo.Farm;
import com.example.farm_management.pojo.FarmFeature;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.FarmService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/farm")
public class FarmController {

    @Autowired
    FarmService farmService;

    private static List<FarmFeature> ConvertStringToFarmFeature(String farmStr) {
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

    private static String ConvertFarmFeatureToString(List<FarmFeature> farmFeatureList) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);

        for (FarmFeature farmFeature : farmFeatureList) {
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("date", sdf.format(farmFeature.getDate()));
            objectNode.put("moisture", farmFeature.getMoisture());
            objectNode.put("nutrient", farmFeature.getNutrient());
            arrayNode.add(objectNode);
        }

        try {
            return mapper.writeValueAsString(arrayNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
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
        // 检查 statistics 字段是否为 List<LinkedHashMap>
        // ? 作为通配符
        if (farm.getStatistics() instanceof List<?>) {
            List<?> statisticsList = (List<?>) farm.getStatistics();
            if (!statisticsList.isEmpty() && statisticsList.get(0) instanceof LinkedHashMap) {
                // 使用 ObjectMapper 将 List<LinkedHashMap> 转换为 List<FarmFeature>
                ObjectMapper mapper = new ObjectMapper();
                List<FarmFeature> farmFeatureList = mapper.convertValue(
                        statisticsList,
                        TypeFactory.defaultInstance().constructCollectionType(List.class, FarmFeature.class)
                );
                // List<FarmFeature> 转换为 String
                String statisticsString = ConvertFarmFeatureToString(farmFeatureList);
                farm.setStatistics(statisticsString);
            } else {
                log.error("统计数据类型不匹配");
                return Result.error("统计数据类型不匹配");
            }
        } else {
            log.error("统计数据不是列表类型");
            return Result.error("统计数据不是列表类型");
        }
        farmService.update(farm);
        return Result.success();
    }
}
