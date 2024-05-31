package com.example.farm_management.service;


import com.example.farm_management.pojo.FertileRecord;

import java.sql.Date;
import java.util.List;

public interface FertileService {
    List<FertileRecord> list(String fid, String farmid, Integer opid, Date optime);

    void add(FertileRecord feedRecord);

    void delete(List<String> ids);

    void update(FertileRecord feedRecord);
}
