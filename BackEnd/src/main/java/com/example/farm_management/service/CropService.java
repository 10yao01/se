package com.example.farm_management.service;

import com.example.farm_management.pojo.Crop;

import java.util.List;

public interface CropService {
    List<Crop> list(String cid,
                    String cname,
                    String cinfo,
                    Integer wtime,
                    Integer ftime);

    void add(Crop crop);

    void delete(List<String> ids);

    void update(Crop crop);


}
