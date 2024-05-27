package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.FarmerMapper;
import com.example.farm_management.pojo.Farmer;
import com.example.farm_management.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmerServiceImpl implements FarmerService {

    @Autowired
    FarmerMapper farmerMapper;

//    @Override
//    public PageBean page(Integer page, Integer pageSize, String name, Short gender, Integer age,
//                         String image, String area, Integer score) {
//
//        PageHelper.startPage(page,pageSize);
//        List<Farmer> farmerList = farmerMapper.list(name,gender,age,image,area,score);
//        Page<Farmer> p = (Page<Farmer>) farmerList;
//
//        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
//        return pageBean;
//    }

    @Override
    public void add(Farmer farmer) {
        farmerMapper.insert(farmer);
    }

    @Override
    public void delete(List<Integer> ids) {
        farmerMapper.delete(ids);
    }

    @Override
    public void update(Farmer farmer) {
        farmerMapper.update(farmer);
    }

    @Override
    public Farmer login(Farmer farmer) {
        return farmerMapper.getByNAP(farmer);
    }

    @Override
    public List<Farmer> list(String name, Short gender, String areaname) {
        return farmerMapper.list(name,gender,areaname);
    }
}
