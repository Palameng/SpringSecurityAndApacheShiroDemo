package com.mengyuan.testshiro.service;

import com.mengyuan.testshiro.model.User;

public interface UserService {
    User findByUsername(String username);
}
