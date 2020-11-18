package com.zheng.service;

import com.zheng.dao.UserFileDao;
import com.zheng.entity.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
@Transactional
public class UserFileServiceImpl implements UserFileService{

    @Autowired
    private UserFileDao userFileDAO;

    @Override
    public List<UserFile> findByUserId(Integer id) {
        return userFileDAO.findByUserId(id);
    }

    @Override
    public void save(UserFile userFile) {
        //是否是图片 含有image
        String isImg = userFile.getType().startsWith("image")?"是":"否";
        userFile.setIsImg(isImg);
        userFile.setDownCounts(0);
        userFile.setUploadTime(new Date());
        userFileDAO.save(userFile);
    }

    @Override
    public UserFile findById(Integer id) {
        return userFileDAO.findById(id);
    }

    @Override
    public void update(UserFile userfile) {
        userFileDAO.update(userfile);
    }

    @Override
    public void delete(Integer id) {
        userFileDAO.delete(id);
    }
}
