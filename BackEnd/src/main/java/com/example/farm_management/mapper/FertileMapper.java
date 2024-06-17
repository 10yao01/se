package com.example.farm_management.mapper;

import com.example.farm_management.pojo.FertileRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Date;
import java.util.List;

@Mapper
public interface FertileMapper {

    List<FertileRecord> list(String fid, String farmid, Integer optype, Date optime);

    @Insert("insert into fertile(f_id, farm_id, op_type, op_time)" +
            "values (#{fid}, #{farmid}, #{optype}, #{optime})")
    void insert(FertileRecord fertileRecord);

    void delete(List<String> ids);

    void update(FertileRecord fertileRecord);
}
