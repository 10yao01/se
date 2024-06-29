package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.LeaveMapper;
import com.example.farm_management.pojo.Leave;
import com.example.farm_management.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    LeaveMapper leaveMapper;

    @Override
    public List<Leave> list(String eid, Date startdate, Date backdate, Integer isdealed) {
        return leaveMapper.list(eid, startdate, backdate, isdealed);
    }

    @Override
    public void add(Leave leave) {
        leaveMapper.insert(leave);
    }

    @Override
    public void delete(List<String> ids) {
        leaveMapper.delete(ids);
    }

    @Override
    public void update(Leave leave) {
        leaveMapper.update(leave);
    }
}
