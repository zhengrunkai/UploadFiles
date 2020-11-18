package com.zheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    //注册
    @GetMapping("zc")
    public String tozc(){
        return "zc";
    }

    //登录
    @GetMapping("login")
    public String tologin(){
        return "login";
    }
}
