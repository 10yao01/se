package com.example.farm_management.service.Impl;

import com.example.farm_management.mapper.UserMapper;
import com.example.farm_management.pojo.PageBean;
import com.example.farm_management.pojo.User;
import com.example.farm_management.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

//    @Override
//    public PageBean page(Integer page, Integer pageSize, String uid,
//                         String name, Integer gender, String tel) {
//
//        PageHelper.startPage(page, pageSize);
//        List<User> farmerList = userMapper.list(uid,name,gender,tel);
//        Page<User> p = (Page<User>) farmerList;
//
//        return new PageBean(p.getTotal(), p.getResult());
//    }

    @Override
    public List<User> list(String uid, String name, Integer gender, String tel) {
        return userMapper.list(uid, name, gender, tel);
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(List<String> ids) {
        userMapper.delete(ids);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public User login(User user){
        return userMapper.getByNAP(user);
    }

    @Override
    public Integer getType(String uid) {
        return userMapper.getType(uid);
    }
}
