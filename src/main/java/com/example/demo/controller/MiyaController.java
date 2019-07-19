package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.ConfigBean;
import com.example.demo.bean.User;

@RestController
@EnableConfigurationProperties({ConfigBean.class,User.class})
public class MiyaController {
	
    @Value("${my.name}")
    private String name;
    @Value("${my.age}")
    private int age;

    @Autowired
    ConfigBean configBean;
    
    @RequestMapping(value = "/miya")
    public String miya(){
//        return name+":"+age;直接读取配置的方式。
        return configBean.getGreeting()+" >>>>"+configBean.getName()+" >>>>"+ configBean.getUuid()+" >>>>"+configBean.getMax();
    }
    
    @Autowired
    User user;
    @RequestMapping(value = "/user")
    public String user(){
        return user.getName()+user.getAge();
    }
}
