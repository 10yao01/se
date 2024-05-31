package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.GoodsMapper;
import com.example.farm_management.pojo.Goods;
import com.example.farm_management.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public List<Goods> list(String gid, String gname) {
        return goodsMapper.list(gid, gname);
    }

    @Override
    public void add(Goods goods) {
        goodsMapper.insert(goods);
    }

    @Override
    public void delete(List<Goods> ids) {
        goodsMapper.delete(ids);
    }

    @Override
    public void update(Goods goods) {
        goodsMapper.update(goods);
    }
}
