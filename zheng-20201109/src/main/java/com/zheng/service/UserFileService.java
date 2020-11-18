package com.zheng.service;

import com.zheng.entity.UserFile;

import java.util.List;

public interface UserFileService {
    List<UserFile> findByUserId(Integer id);

    void save(UserFile userFile);

    UserFile findById(Integer id);

    void update(UserFile userFile);

    void delete(Integer id);
}
