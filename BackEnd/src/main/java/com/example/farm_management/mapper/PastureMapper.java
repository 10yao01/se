package com.example.farm_management.mapper;


import com.example.farm_management.pojo.Pasture;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PastureMapper {

    List<Pasture> list(String pname, String pid, String wid, String posinfo, String poultryid, String poultryname);

    @Insert("insert into pasture(p_id,p_name,poultry_id,poultry_name,poultry_num,start_time,w_id,mature_time,stage,longitude,latitude,area,pos_info)" +
            "values (#{pid},#{pname},#{poultryid},#{poultryname},#{poultrynum},#{starttime},#{wid},#{maturetime},#{stage},#{longitude},#{latitude},#{area},#{posinfo})")
    void insert(Pasture pasture);

    void delete(List<String> ids);

    void update(Pasture pasture);
}
