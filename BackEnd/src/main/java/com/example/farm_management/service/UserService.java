package com.example.farm_management.service;

import com.example.farm_management.pojo.User;

import java.util.List;

public interface UserService {

    List<User> list(String uid, String name, Integer gender, String tel);

    void add(User user);

    void delete(List<Integer> ids);

    void update(User user);
}
