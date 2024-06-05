package com.example.farm_management.service;


import com.example.farm_management.pojo.Goods;

import java.util.List;

public interface GoodsService {

    List<Goods> list(String gid, String gname);

    void add(Goods goods);

    void delete(List<String> ids);

    void update(Goods goods);
}
