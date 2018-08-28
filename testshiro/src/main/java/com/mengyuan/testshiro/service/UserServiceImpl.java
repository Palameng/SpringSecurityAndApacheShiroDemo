package com.mengyuan.testshiro.service;

import com.mengyuan.testshiro.mapper.UserMapper;
import com.mengyuan.testshiro.model.User;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
