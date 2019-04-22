package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.modules.mapper.UserLoginMapper;
import com.mtons.mblog.modules.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    UserLoginMapper userLoginMapper;


    @Override
    public Boolean UserLogin(String username, String password) {
        return userLoginMapper.UserLogin(username,password);
    }
}
