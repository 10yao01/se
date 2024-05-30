package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.PastureMapper;
import com.example.farm_management.pojo.Pasture;
import com.example.farm_management.service.PastureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PastureServiceImpl implements PastureService {

    @Autowired
    PastureMapper pastureMapper;

    @Override
    public List<Pasture> list(String pname, String pid, String posinfo, String poultryid, String poultryname) {
        return pastureMapper.list(pname, pid, posinfo, poultryid, poultryname);
    }

    @Override
    public void add(Pasture pasture){
        pastureMapper.insert(pasture);
    }

    @Override
    public void delete(List<Integer> ids) {
        pastureMapper.delete(ids);
    }

    @Override
    public void update(Pasture pasture) {
        pastureMapper.update(pasture);
    }
}
