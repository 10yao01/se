package com.example.farm_management.service;

import com.example.farm_management.pojo.Leave;

import java.sql.Date;
import java.util.List;

public interface LeaveService {

    List<Leave> list(String eid,
                     Date startdate,
                     Date backdate,
                     Integer isdealed);

    void add(Leave leave);

    void delete(List<String> ids);

    void update(Leave leave);
}
