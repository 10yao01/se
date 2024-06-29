package com.example.farm_management.service;

import com.example.farm_management.pojo.Clock;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface ClockService {

    List<Clock> list(String eid,
                     Date date,
                     Time come,
                     Time leave);

    void add(Clock clock);

    void delete(List<String> ids);

    void update(Clock clock);

}
