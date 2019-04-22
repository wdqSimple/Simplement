package com.mtons.mblog.modules.service;

import org.springframework.stereotype.Service;

@Service
public interface UserMessageUpdateService {

    //更新基本信息---昵称、个性签名。
    Boolean updateBaseMessage(String name,String signature,Integer id);

    //更新密码
    Boolean updatePassword(String password,Integer id);

    //更新邮箱
    //1.查询当前需要更新的邮箱是否被使用
    Boolean selectEmail(String emali,Integer id);
    //更新邮箱
    Boolean updateEmali(String email,Integer id);


}
