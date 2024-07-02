package com.example.farm_management.mapper;



import com.example.farm_management.pojo.Farm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FarmMapper {

    List<Farm> list(String fname, String fid, String wid, String posinfo, String cname, String cid, Integer soiltype);

    @Insert("insert into farm(f_id,f_name,c_id,c_name,c_amount,p_date,w_id,harvest_date,stage,pos_info,longitude,latitude,area,soil_type,statistics)" +
            "values (#{fid},#{fname},#{cid},#{cname},#{camount},#{pdate},#{wid},#{harvestdate},#{stage},#{posinfo},#{longitude},#{latitude},#{area},#{soiltype},#{statistics})")
    void insert(Farm farm);

    void delete(List<String> ids);

    void update(Farm farm);
}
