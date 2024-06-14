package com.example.farm_management.service;

import com.example.farm_management.pojo.PageBean;
import com.example.farm_management.pojo.User;

import java.util.List;

public interface UserService {


    PageBean page(Integer page, Integer pageSize, String uid,
                  String name, Integer gender,  String tel);

//    List<User> list(String uid, String name, Integer gender, String tel);

    void add(User user);

    void delete(List<String> ids);

    void update(User user);

    User login(User user);

    Integer getType(String uid);
}
