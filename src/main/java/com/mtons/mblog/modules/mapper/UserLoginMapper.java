package com.mtons.mblog.modules.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginMapper {

    //登陆
    Boolean UserLogin(String username,String password);
}
