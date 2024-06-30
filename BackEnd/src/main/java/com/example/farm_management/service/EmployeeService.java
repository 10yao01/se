package com.example.farm_management.service;

import com.example.farm_management.pojo.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> list(String eid, String ename, String tel, Integer salary, Integer isgot);

    void add(Employee employee);

    void delete(List<String> ids);

    void update(Employee employee);

}
