package com.zheng.service;

import com.zheng.dao.UserDao;
import com.zheng.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public void zc(User user) {
        userDao.zc(user);
    }

    @Override
    public User login(User user) {
        return userDao.login(user);
    }
}
