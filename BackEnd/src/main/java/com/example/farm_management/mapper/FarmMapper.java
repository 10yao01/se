package com.example.farm_management.mapper;



import com.example.farm_management.pojo.Farm;
import com.example.farm_management.pojo.Farmer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FarmMapper {

    List<Farm> list(String fname, String fid, String pos);

    @Insert("insert into farmer(f_id,f_name,c_id,c_name,c_amount,p_date,w_id,harvest_date,stage,pos_info)" +
            "values (#{fid},#{name},#{cid},#{cname},#{camount},#{wid},#{pdate},#{harvest},#{stage},#{pos})")
    void insert(Farm farm);

    void delete(List<Integer> ids);

    void update(Farm farm);
}
