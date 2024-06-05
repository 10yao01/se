package com.example.farm_management.mapper;


import com.example.farm_management.pojo.Goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {

    List<Goods> list(String gid, String gname);

    @Insert("insert into goods(g_id,g_name,stock,sale,price,score,rater_num)" +
            "values (#{gid},#{gname},#{stock},#{sale},#{price},#{score},#{raternum})")
    void insert(Goods goods);

    void delete(List<String> ids);

    void update(Goods goods);
}
