package com.example.farm_management.service;


import com.example.farm_management.pojo.Farm;

import java.util.List;

public interface FarmService {

    List<Farm> list(String fname, String fid, String wid, String posinfo, String cname, String cid, Integer soiltype);

    void add(Farm farm);

    void delete(List<String> ids);

    void update(Farm farm);
}
