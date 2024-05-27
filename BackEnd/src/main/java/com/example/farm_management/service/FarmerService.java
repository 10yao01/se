package com.example.farm_management.service;

import com.example.farm_management.pojo.Farmer;

import java.util.List;

public interface FarmerService {
//    PageBean page(Integer page, Integer pageSize, String name, Short gender,
//                  Integer age, String image, String area, Integer score);

    void add(Farmer farmer);

    void delete(List<Integer> ids);

    void update(Farmer farmer);

    Farmer login(Farmer farmer);

    List<Farmer> list(String name, Short gender, String areaname);


}
