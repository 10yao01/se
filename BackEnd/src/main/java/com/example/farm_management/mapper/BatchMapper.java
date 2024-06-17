package com.example.farm_management.mapper;

import com.example.farm_management.pojo.Batch;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Date;
import java.util.List;

@Mapper
public interface BatchMapper {

    List<Batch> list(String bid,
                     Date bdate,
                     String originid,
                     String gid,
                     String gname,
                     Integer isdealed,
                     Integer amount);

    @Insert("insert into batch(b_id, b_date, origin_id, g_id, g_name, is_dealed, amount)" +
            "values (#{bid},#{bdate},#{originid},#{gid},#{gname},#{isdealed},#{amount})")
    void insert(Batch batch);

    void delete(List<String> ids);

    void update(Batch batch);
}
