package com.zheng.dao;

import com.zheng.entity.UserFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserFileDao {
    //根据Id查询该用户的所有文件
    List<UserFile> findByUserId(Integer id);

    //保存用户的文件记录
    void save(UserFile userFile);

    //根据文件id获取文件信息
    UserFile findById(Integer id);

    //根据id更新下载次数
    void update(UserFile userFile);

    //根据id删除记录
    void delete(Integer id);
}
