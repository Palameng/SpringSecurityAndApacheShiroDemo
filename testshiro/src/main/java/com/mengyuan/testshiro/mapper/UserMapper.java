package com.mengyuan.testshiro.mapper;

import com.mengyuan.testshiro.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findByUsername(@Param("username") String username);
}
