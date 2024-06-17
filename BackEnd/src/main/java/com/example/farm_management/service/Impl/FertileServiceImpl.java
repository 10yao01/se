package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.FertileMapper;
import com.example.farm_management.pojo.FertileRecord;
import com.example.farm_management.service.FertileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class FertileServiceImpl implements FertileService {

    @Autowired
    FertileMapper fertileMapper;

    @Override
    public List<FertileRecord> list(String fid, String farmid, Integer optype, Date optime){
        return fertileMapper.list(fid, farmid,optype,optime);
    }

    @Override
    public void add(FertileRecord fertileRecord){
        fertileMapper.insert(fertileRecord);
    }

    @Override
    public void delete(List<String> ids){
        fertileMapper.delete(ids);
    }

    @Override
    public void update(FertileRecord fertileRecord){
        fertileMapper.update(fertileRecord);
    }


}
