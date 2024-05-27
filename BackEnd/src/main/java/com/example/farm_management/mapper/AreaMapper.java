package com.example.farm_management.mapper;

import com.example.farm_management.pojo.Area;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AreaMapper {

    @Select("select * from area")
    List<Area> list();

    @Delete("delete from area where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into area(id,name,extent)" +
            "values(#{id},#{name},#{extent})")
    void add(Area area);

    @Select("select * from area where name like concat('%', #{name}, '%')")
    List<Area> search(String name);
}
