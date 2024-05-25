package com.example.credit_score_management.mapper;

import com.example.credit_score_management.pojo.Incident;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IncidentMapper {


    List<Incident> list(String title, String name, Integer classes);

    @Delete("delete from incident where Iid = #{id}")
    void deleteById(Integer id);

    @Insert("insert into incident(Iid,title,fid,name,description,classes,degree)" +
            "values(#{Iid},#{title},#{fid},#{name},#{description},#{classes},#{degree})")
    void add(Incident incident);

    @Update("update incident set isdeal=1 where Iid=#{id}")
    void updateIsdeal(Integer id);
}
