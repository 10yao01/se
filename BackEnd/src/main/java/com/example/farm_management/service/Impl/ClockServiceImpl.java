package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.ClockMapper;
import com.example.farm_management.pojo.Clock;
import com.example.farm_management.service.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
public class ClockServiceImpl implements ClockService {

    @Autowired
    ClockMapper clockMapper;

    @Override
    public List<Clock> list(String eid, Date date, Time come, Time leave) {
        return clockMapper.list(eid, date, come, leave);
    }

    @Override
    public void add(Clock clock) {
        clockMapper.insert(clock);
    }

    @Override
    public void delete(List<String> ids) {
        clockMapper.delete(ids);
    }

    @Override
    public void update(Clock clock) {
        clockMapper.update(clock);
    }
}
