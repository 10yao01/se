package com.example.farm_management.service;

import com.example.farm_management.pojo.Area;

import java.util.List;

public interface AreaService {

    List<Area> list();

    void delete(Integer id);

    void add(Area area);

    List<Area> search(String name);
}
