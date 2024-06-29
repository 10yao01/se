package com.example.farm_management.service;

import com.example.farm_management.pojo.Order;

import java.util.List;

public interface OrderService {
    List<Order> list(String oid,
                     String customerid,
                     String gid,
                     String gname,
                     Integer amount,
                     Integer score,
                     String eid,
                     Double price);

    void add(Order order);

    void delete(List<String> ids);

    void update(Order order);
}
