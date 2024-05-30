package com.example.farm_management.mapper;


import com.example.farm_management.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {


    List<User> list(String uid, String name, Integer gender, String tel);

    @Insert("insert into farm(u_id,pwd,id_type,name,gender,age,tel)" +
            "values (#{uid},#{pwd},#{idtype},#{name},#{gender},#{age},#{tel})")
    void insert(User user);

    void delete(List<Integer> ids);

    void update(User user);
}
