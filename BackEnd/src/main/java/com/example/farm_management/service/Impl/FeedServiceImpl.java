package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.FeedMapper;
import com.example.farm_management.pojo.FeedRecord;
import com.example.farm_management.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    FeedMapper feedMapper;

    @Override
    public List<FeedRecord> list(String fid, String pid, Date ftime){
        return feedMapper.list(fid,pid,ftime);
    }

    @Override
    public void add(FeedRecord feedRecord){
        feedMapper.insert(feedRecord);
    }

    @Override
    public void delete(List<Integer> ids){
        feedMapper.delete(ids);
    }

    @Override
    public void update(FeedRecord feedRecord){
        feedMapper.update(feedRecord);
    }
}
