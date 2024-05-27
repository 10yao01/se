package com.example.farm_management.mapper;

import com.example.farm_management.pojo.Farmer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FarmerMapper {

//    List<Farmer> list(String name, Short gender, Integer age,
//                      String image, String area, Integer score);

    @Insert("insert into farmer(fid,name,gender,age,image,areaname)" +
            "values (#{fid},#{name},#{gender},#{age},#{image},#{areaname})")
    void insert(Farmer farmer);

    void delete(List<Integer> ids);

    void update(Farmer farmer);

    @Select("select * from farmer where name=#{name} and password =  #{password}")
    Farmer getByNAP(Farmer farmer);

    List<Farmer> list(String name, Short gender, String areaname);
}
