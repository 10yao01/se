package com.example.farm_management.mapper;

import com.example.farm_management.pojo.Leave;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Date;
import java.util.List;

@Mapper
public interface LeaveMapper {

    List<Leave> list(String eid,
                     Date startdate,
                     Date backdate,
                     Integer isdealed);

    @Insert("insert into `leave`(e_id, start_date, back_date, is_dealed)" +
            "values (#{eid},#{startdate},#{backdate},#{isdealed})")
    void insert(Leave leave);

    void delete(List<String> ids);

    void update(Leave leave);

}
