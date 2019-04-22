package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.modules.mapper.UserMessageUpdateMapper;
import com.mtons.mblog.modules.service.UserMessageUpdateService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMessageUpdateServiceImpl implements UserMessageUpdateService {

    @Autowired
    UserMessageUpdateMapper userMessageUpdateMapper;


    //更新基础信息  ----昵称 个性签名
    public Boolean updateBaseMessage(String name, String signature, Integer id) {
        return userMessageUpdateMapper.updateBaseMessage(name, signature, id);
    }


    //更新密码
    public Boolean updatePassword(String password, Integer id) {
        return userMessageUpdateMapper.updatePassword(password, id);
    }

    //更新邮箱信息
    //1.查询邮箱，是否被使用
    public Boolean selectEmail(String emali, Integer id) {
        return userMessageUpdateMapper.selectEmail(emali, id);
    }

    //更新邮箱信息
    public Boolean updateEmali(String email, Integer id) {
        return userMessageUpdateMapper.updateEmali(email, id);
    }
}
