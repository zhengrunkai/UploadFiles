package com.zheng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zheng.dao")
public class FilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilesApplication.class, args);
    }

}
