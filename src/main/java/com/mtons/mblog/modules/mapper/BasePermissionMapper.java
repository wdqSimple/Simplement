package com.mtons.mblog.modules.mapper;

import com.mtons.mblog.modules.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Mapper
public interface BasePermissionMapper {

    //根据ID查询全部
    List<Permission> findAllByParentId(int parentId, Sort sort);

    //根据ID查询用户数量
    int countUsed(@Param("permId") long permId);

    //最大用户数量
    int maxWeight();

    Page<Permission> findAll();

    //查询全部数据
    List<Permission> findAll(Sort sort);

    //根据ID查询
    List<Permission> findById(Long id);
}
