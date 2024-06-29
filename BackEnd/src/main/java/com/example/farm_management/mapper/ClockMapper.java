package com.example.farm_management.mapper;

import com.example.farm_management.pojo.Clock;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Mapper
public interface ClockMapper {

    List<Clock> list(String eid,
                     Date date,
                     Time come,
                     Time leave);

    @Insert("insert into clock(e_id, date, come, `leave`)" +
            "values (#{eid},#{date},#{come},#{leave})")
    void insert(Clock clock);

    void delete(List<String> ids);

    void update(Clock clock);
}
