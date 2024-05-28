package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.IncidentMapper;
import com.example.farm_management.pojo.Incident;
import com.example.farm_management.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentServiceImpl implements IncidentService {

    @Autowired
    private IncidentMapper incidentMapper;

    @Override
    public List<Incident> list(String title, String name, Integer classes) {
        return incidentMapper.list(title, name, classes);
    }

    @Override
    public void delete(Integer id) {
        incidentMapper.deleteById(id);
    }

    @Override
    public void add(Incident incident) {
        incidentMapper.add(incident);
    }

    @Override
    public void updateIsdeal(Integer id) {
        incidentMapper.updateIsdeal(id);
    }
}
