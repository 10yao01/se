package com.example.farm_management.service;

import com.example.farm_management.pojo.Batch;

import java.sql.Date;
import java.util.List;

public interface BatchService {

    List<Batch> list(String bid,
                     Date bdate,
                     String originid,
                     String gid,
                     String gname,
                     Integer isdealed,
                     Integer amount);

    void add(Batch batch);

    void delete(List<String> ids);

    void update(Batch batch);
}
