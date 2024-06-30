package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.EmployeeMapper;
import com.example.farm_management.pojo.Employee;
import com.example.farm_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public List<Employee> list(String eid, String ename, String tel, Integer salary, Integer isgot) {
        return employeeMapper.list(eid, ename, tel, salary, isgot);
    }

    @Override
    public void add(Employee employee) {
        employeeMapper.insert(employee);
    }

    @Override
    public void delete(List<String> ids) {
        employeeMapper.delete(ids);
    }

    @Override
    public void update(Employee employee) {
        employeeMapper.update(employee);
    }
}
