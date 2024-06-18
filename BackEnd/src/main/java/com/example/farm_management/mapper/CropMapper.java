package com.example.farm_management.mapper;

import com.example.farm_management.pojo.Crop;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CropMapper {

    List<Crop> list(String cid,
                    String cname,
                    String cinfo,
                    Integer wtime,
                    Integer ftime);

    @Insert("insert into crop(c_id,c_name,c_info,w_time,f_time)" +
            "values (#{cid},#{cname},#{cinfo},#{wtime},#{ftime})")
    void insert(Crop crop);

    void delete(List<String> ids);

    void update(Crop crop);


}
