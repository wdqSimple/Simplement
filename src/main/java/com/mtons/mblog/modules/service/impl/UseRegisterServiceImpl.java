package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.base.lang.EntityStatus;
import com.mtons.mblog.base.utils.MD5;
import com.mtons.mblog.modules.entity.User;
import com.mtons.mblog.modules.mapper.UseRegisterMapper;
import com.mtons.mblog.modules.service.UseRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.Calendar;
import java.util.Date;


@Service("UseRegisterService")
public class UseRegisterServiceImpl implements UseRegisterService {

    @Autowired
    UseRegisterMapper userRegisterMapper;



    @Override
    public User saveRegisterMessage(User user) {
        Assert.notNull(user, "Parameter user can not be null!");
        Assert.hasLength(user.getUsername(), "用户名不能为空!");
        Assert.hasLength(user.getPassword(), "密码不能为空!");
        String userName=user.getUsername();
        String check = userRegisterMapper.chenckedUsername(userName);
        if(!check.equals("null")){
            Assert.isNull(check, "用户名已经存在!");
        }
        User po = new User();
        BeanUtils.copyProperties(user, po);
        if (StringUtils.isBlank(po.getName())) {
            po.setName(user.getUsername());
        }
        Date now = Calendar.getInstance().getTime();
        po.setPassword(MD5.md5(user.getPassword()));
        po.setStatus(EntityStatus.ENABLED);
        po.setCreated(now);
        return userRegisterMapper.saveRegisterMessage(po);
    }
}
