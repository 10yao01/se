package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.AreaMapper;
import com.example.farm_management.pojo.Area;
import com.example.farm_management.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    AreaMapper areaMapper;

    @Override
    public List<Area> list() {
        return areaMapper.list();
    }

    @Override
    public void delete(Integer id) {
        areaMapper.deleteById(id);
    }

    @Override
    public void add(Area area) {
        areaMapper.add(area);
    }

    @Override
    public List<Area> search(String name) {
        return areaMapper.search(name);
    }

}
