package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.BatchMapper;
import com.example.farm_management.pojo.Batch;
import com.example.farm_management.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    BatchMapper batchMapper;


    @Override
    public List<Batch> list(String bid, Date bdate, String originid, String gid, String gname, Integer isdealed, Integer amount) {
        return batchMapper.list(bid, bdate, originid, gid, gname, isdealed, amount);
    }

    @Override
    public void add(Batch batch) {
        batchMapper.insert(batch);
    }

    @Override
    public void delete(List<String> ids) {
        batchMapper.delete(ids);
    }

    @Override
    public void update(Batch batch) {
        batchMapper.update(batch);
    }
}
