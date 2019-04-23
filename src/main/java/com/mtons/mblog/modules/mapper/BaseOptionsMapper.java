package com.mtons.mblog.modules.mapper;

import com.mtons.mblog.modules.entity.Options;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BaseOptionsMapper {
    //查询通过Key
    Options findByKey(String key);

    //查询全部
    List<Options> findAll();

    //保存设置
    Boolean save(Options options);
}
