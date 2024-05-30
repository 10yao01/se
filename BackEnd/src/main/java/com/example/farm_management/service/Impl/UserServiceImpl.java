package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.UserMapper;
import com.example.farm_management.pojo.User;
import com.example.farm_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> list(String uid, String name, Integer gender, String tel) {
        return userMapper.list(uid, name, gender, tel);
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(List<Integer> ids) {
        userMapper.delete(ids);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }
}
