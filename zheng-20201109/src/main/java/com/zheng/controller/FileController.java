package com.zheng.controller;

import com.zheng.entity.User;
import com.zheng.entity.UserFile;
import com.zheng.service.UserFileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping
public class FileController {

    @Autowired
    private UserFileService userFileService;

    //展示用户所有文件数据
    @GetMapping("showAll")
    public String findAll(HttpSession session, Model model){
        //在登录的session中获取用户的Id
        User user= (User) session.getAttribute("user");
        //根据用户id查找有的文件信息
        List<UserFile> byUserId=userFileService.findByUserId(user.getId());
        //存入作用域中
        model.addAttribute("files",byUserId);
        return "showAll";
   }
    //上传文件的处理 并保持文件信息保存到数据库
    @PostMapping("/upload")
    public String upload(MultipartFile myfile, HttpSession session) throws IOException {

        //获取上传文件用户id
        User user = (User) session.getAttribute("user");
        //获取文件的原始名称
        String oldFileName = myfile.getOriginalFilename();
        //获取文件的后缀
        String extension = "." + FilenameUtils.getExtension(myfile.getOriginalFilename());
        //生成新的文件名称
        //randomUUID 使新名称不一样（不起冲突）
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + UUID.randomUUID().toString().replace("-", "").substring(6) + extension;
        //获取文件大小
        long size = myfile.getSize();
        //文件类型
        String type = myfile.getContentType();
        //处理根据日期生成目录
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "static/files";
        System.out.println(realPath);
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = realPath + "/" + format;
        File dataDir = new File(dateDirPath);
        if (!dataDir.exists()) dataDir.mkdirs();

        //处理文件上传
        myfile.transferTo(new File(dataDir,newFileName));

        //将文件信息放入数据库保存
        UserFile userFile = new UserFile();
        userFile.setOldFileName(oldFileName);
        userFile.setNewFileName(newFileName);
        userFile.setExt(extension);
        userFile.setSize(String.valueOf(size));
        userFile.setType(type);
        userFile.setPath("/files/" + format);
        userFile.setUserId(user.getId());
        userFileService.save(userFile);
        return "redirect:/showAll";
    }

    //文件下载
    @GetMapping("/download")
    public void download(Integer id,String openStyle, HttpServletResponse response) throws IOException {
        //获取文件信息
        UserFile userFile = userFileService.findById(id);
        //判断用户是下载还是在线打开
        //见showAll.html页面，下载openStyle==null，在线打开openStyle==attachment
        openStyle = (openStyle == null ? "attachment" : openStyle);
        if ("attachment".equals(openStyle)){
            //更新文件下载次数
            userFile.setDownCounts(userFile.getDownCounts()+1);
            userFileService.update(userFile);
        }
        //根据文件信息中文件的名字和文件存储的路径获取文件输入流
        String realpath = ResourceUtils.getURL("classpath:").getPath()
                + "/static" + userFile.getPath();
        //获取文件输入流
        FileInputStream is = new FileInputStream(new File(realpath, userFile.getNewFileName()));
        //附件下载
        response.setHeader("content-disposition",openStyle+";fileName="
                + URLEncoder.encode(userFile.getOldFileName(),"UTF-8"));
        //获取响应输出流
        ServletOutputStream os = response.getOutputStream();
        //文件拷贝
        IOUtils.copy(is,os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }

    //删除文件信息
    @GetMapping("/delete")
    public String delete(Integer id) throws FileNotFoundException {
        //根据Id查询信息
        UserFile userFile = userFileService.findById(id);

        //删除文件
        String realpath = ResourceUtils.getURL("classpath:").getPath() + "/static" + userFile.getPath();
        File file = new File(realpath, userFile.getNewFileName());
        if (file.exists()){
            file.delete();//立即删除
        }

        //删除数据库中的记录
        userFileService.delete(id);
        return "redirect:/showAll";
    }

    //返回当前用户的所有文件列表--json格式数据
    @GetMapping("findAllJson")
    @ResponseBody
    public List<UserFile> findAllJson(HttpSession session){
        //在登录的session中获取用户的Id
        User user= (User) session.getAttribute("user");
        //根据用户id查找有的文件信息
        List<UserFile> userFiles=userFileService.findByUserId(user.getId());
        return userFiles;
    }
}
