package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UseRegisterService {



    //保存当前注册信息
    User saveRegisterMessage(User user);

}

