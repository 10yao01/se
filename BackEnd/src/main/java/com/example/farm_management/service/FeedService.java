package com.example.farm_management.service;

import com.example.farm_management.pojo.FeedRecord;

import java.sql.Date;
import java.util.List;

public interface FeedService {

    List<FeedRecord> list(String fid, String pid, Date ftime);

    void add(FeedRecord feedRecord);

    void delete(List<Integer> ids);

    void update(FeedRecord feedRecord);
}
