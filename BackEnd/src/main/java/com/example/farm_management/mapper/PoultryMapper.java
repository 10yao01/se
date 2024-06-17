package com.example.farm_management.mapper;


import com.example.farm_management.pojo.Poultry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PoultryMapper {

    List<Poultry> list(String pid,
                       String pname,
                       String pinfo,
                       Integer feedinterval);

    @Insert("insert into poultry(p_id, p_name, p_info, feed_interval)" +
            "values (#{pid},#{pname},#{pinfo},#{feedinterval})")
    void insert(Poultry poultry);

    void delete(List<String> ids);

    void update(Poultry poultry);

}
