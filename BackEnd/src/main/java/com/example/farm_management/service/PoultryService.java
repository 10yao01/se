package com.example.farm_management.service;

import com.example.farm_management.pojo.Poultry;

import java.util.List;

public interface PoultryService {
    List<Poultry> list(String pid,
                       String pname,
                       String pinfo,
                       Integer feedinterval);

    void add(Poultry poultry);

    void delete(List<String> ids);

    void update(Poultry poultry);

}
