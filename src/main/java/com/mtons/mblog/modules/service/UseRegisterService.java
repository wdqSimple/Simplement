package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface UseRegisterService {

    //查询当前名字是否被占用
    String selectUsername(@Param("username") String username);

    //保存当前注册信息
    User saveRegisterMessage(User user);

}

