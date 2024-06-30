package com.example.farm_management.controller;

import com.example.farm_management.pojo.Employee;
import com.example.farm_management.pojo.Performance;
import com.example.farm_management.pojo.Result;
import com.example.farm_management.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    private static List<Performance> ConvertStringToPerformance(String performanceStr) {
        List<Performance> performanceList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(performanceStr);
            for (JsonNode node : rootNode) {
                String dateStr = node.get("date").asText();
                Integer performance = node.get("performance").asInt();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);
                Date date = new Date(sdf.parse(dateStr).getTime());
                Performance performance1 = new Performance(date, performance);
                performanceList.add(performance1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return performanceList;
    }

    private static String ConvertPerformanceToString(List<Performance> performanceList) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);

        for (Performance performance : performanceList) {
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("date", sdf.format(performance.getDate()));
            objectNode.put("performance", performance.getPerformance());
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
    public Result list(String eid, String ename, String tel, Integer salary, Integer isgot) {
        log.info("查询职工信息");
        List<Employee> employeeList = employeeService.list(eid, ename, tel, salary, isgot);
        for (Employee employee : employeeList) {
            Object performanceList = employee.getPerformance();
            employee.setPerformance(ConvertStringToPerformance((String) performanceList));
        }
        return Result.success(employeeList);
    }

    // 这里限制新增的话，不对员工绩效进行新增
    // 员工绩效在修改时使用
    @PostMapping
    public Result add(@RequestBody Employee employee) {
        log.info("新增职工信息：{}", employee);
        try{
            employeeService.add(employee);
        }
        catch (Exception e){
            return Result.error("添加错误");
        }
        return Result.success();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<String> ids) {
        log.info("批量删除操作, ids:{}", ids);
        employeeService.delete(ids);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Employee employee) {
        log.info("更新职工信息:{}", employee);

        if (employee.getPerformance() instanceof List<?>) {
            List<?> performanceList = (List<?>) employee.getPerformance();
            if (!performanceList.isEmpty() && performanceList.get(0) instanceof LinkedHashMap) {
                ObjectMapper mapper = new ObjectMapper();
                List<Performance> performance = mapper.convertValue(performanceList, TypeFactory.defaultInstance().constructCollectionType(List.class, Performance.class));
                String performanceString = ConvertPerformanceToString(performance);
                employee.setPerformance(performanceString);
            } else {
                log.error("统计数据类型不匹配");
                return Result.error("统计数据类型不匹配");
            }
        } else {
            log.error("统计数据不是列表类型");
            return Result.error("统计数据不是列表类型");
        }
        employeeService.update(employee);
        return Result.success();
    }
}
