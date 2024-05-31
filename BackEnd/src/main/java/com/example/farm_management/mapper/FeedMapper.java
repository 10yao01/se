package com.example.farm_management.mapper;

import com.example.farm_management.pojo.FeedRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Date;
import java.util.List;

@Mapper
public interface FeedMapper {

    List<FeedRecord> list(String fid, String pid, Date ftime);

    @Insert("insert into feed(f_id, p_id, f_time)" +
            "values (#{fid},#{pid},#{ftime})")
    void insert(FeedRecord feedRecord);

    void delete(List<String> ids);

    void update(FeedRecord feedRecord);
}
