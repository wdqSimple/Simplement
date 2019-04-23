package com.mtons.mblog.modules.mapper;

import com.mtons.mblog.modules.entity.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BasePostAttributeMapper {

    //查询全部信息
    List<Post> findAll();

    //删除单个信息
    Boolean deleteByID(Long id);

    //添加信息
    Boolean save(Post post);




}
