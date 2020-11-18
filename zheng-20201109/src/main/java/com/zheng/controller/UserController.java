package com.zheng.controller;

import com.zheng.entity.User;
import com.zheng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    //注册
    @RequestMapping("/zc")
    public String zc(User user,HttpSession session){
        userService.zc(user);
            return "redirect:/login";
    }

    //登录页面
    @RequestMapping("/login")
    public String login(User user, HttpSession session){
        User userDB=userService.login(user);
        if(userDB!=null){
            session.setAttribute("user",userDB);
            return "redirect:/showAll";
        }else{
            //用户或密码错误重定向回login页面
            return "redirect:/login";
        }
    }
}
