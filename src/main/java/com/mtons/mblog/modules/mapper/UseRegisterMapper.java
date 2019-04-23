package com.mtons.mblog.modules.mapper;

import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.Set;


@Mapper
public interface UseRegisterMapper {

    //查询当前名字是否被占用
    String selectUsername(@Param("username") String username);

    //保存当前注册信息
    User saveRegisterMessage(User user);

    Map<Long, UserVO> findMapByIds(Set<Long> ids);

}
