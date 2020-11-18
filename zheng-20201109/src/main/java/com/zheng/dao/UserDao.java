package com.zheng.dao;

import com.zheng.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    //用户注册
    void zc(User user);

    //用户登录
    User login(User user);
}
