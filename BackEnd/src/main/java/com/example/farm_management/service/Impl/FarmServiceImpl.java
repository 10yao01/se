package com.example.farm_management.service.Impl;



import com.example.farm_management.mapper.FarmMapper;
import com.example.farm_management.pojo.Farm;
import com.example.farm_management.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {

    @Autowired
    FarmMapper farmMapper;

    @Override
    public List<Farm> list(String fname, String fid, String wid, String posinfo, String cname, String cid, Integer soiltype) {
        return farmMapper.list(fname, fid, wid, posinfo, cname, cid, soiltype);
    }

    @Override
    public void add(Farm farm){
        farmMapper.insert(farm);
    }

    @Override
    public void delete(List<String> ids) {
        farmMapper.delete(ids);
    }

    @Override
    public void update(Farm farm) {
        farmMapper.update(farm);
    }
}
