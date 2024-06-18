package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.OrderMapper;
import com.example.farm_management.pojo.Order;
import com.example.farm_management.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<Order> list(String oid, String customerid, String gid, String gname, Integer amount, Integer score) {
        return orderMapper.list(oid, customerid, gid, gname, amount, score);
    }

    @Override
    public void add(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public void delete(List<String> ids) {
        orderMapper.delete(ids);
    }

    @Override
    public void update(Order order) {
        orderMapper.update(order);
    }
}
