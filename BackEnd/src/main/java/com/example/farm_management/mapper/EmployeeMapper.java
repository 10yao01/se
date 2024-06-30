package com.example.farm_management.mapper;

import com.example.farm_management.pojo.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<Employee> list(String eid, String ename, String tel, Integer salary, Integer isgot);

    @Insert("insert into employee(e_id, e_name, performance, tel, salary, is_got)"+
            "values (#{eid},#{ename},#{performance},#{tel},#{salary},#{isgot})")
    void insert(Employee employee);

    void delete(List<String> ids);

    void update(Employee employee);

}
