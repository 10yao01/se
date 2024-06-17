package com.example.farm_management.service.Impl;


import com.example.farm_management.mapper.PoultryMapper;
import com.example.farm_management.pojo.Poultry;
import com.example.farm_management.service.PoultryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoultryServiceImpl implements PoultryService {

    @Autowired
    PoultryMapper poultryMapper;


    @Override
    public List<Poultry> list(String pid, String pname, String pinfo, Integer feedinterval) {
        return poultryMapper.list(pid, pname, pinfo, feedinterval);
    }

    @Override
    public void add(Poultry poultry) {
        poultryMapper.insert(poultry);
    }

    @Override
    public void delete(List<String> ids) {
        poultryMapper.delete(ids);
    }

    @Override
    public void update(Poultry poultry) {
        poultryMapper.update(poultry);
    }
}
