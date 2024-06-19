package com.example.farm_management.service;

import com.example.farm_management.pojo.Pasture;

import java.util.List;

public interface PastureService {

    List<Pasture> list(String pname, String pid, String wid, String posinfo, String poultryid, String poultryname);

    void add(Pasture pasture);

    void delete(List<String> ids);

    void update(Pasture pasture);
}
