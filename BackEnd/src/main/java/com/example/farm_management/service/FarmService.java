package com.example.farm_management.service;


import com.example.farm_management.pojo.Farm;

import java.util.List;

public interface FarmService {

    List<Farm> list(String fname, String fid, String pos, String cname, String cid);

    void add(Farm farm);

    void delete(List<Integer> ids);

    void update(Farm farm);
}
