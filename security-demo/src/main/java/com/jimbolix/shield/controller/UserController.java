package com.jimbolix.shield.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/test")
    public Map<String,String> test(){
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("key","这是个测试");
        return resultMap;
    }
}
