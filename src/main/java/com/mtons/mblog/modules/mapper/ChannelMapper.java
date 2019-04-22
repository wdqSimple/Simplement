package com.mtons.mblog.modules.mapper;

import com.mtons.mblog.modules.entity.Channel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Sort;

import java.util.*;

@Mapper
public interface ChannelMapper {

    //查询当前
    List<Channel> findAllByStatus(int status, Sort sort);

    //查询全部
    List<Channel> SelectAllStatus();

    //删除
    Boolean DelStatus(int id);

    //获取最大数
    int maxWeight();

    //查询全部
    List<Channel> findAllBySort(Sort sort);

    //查询全部
    List<Channel> findAll(Sort sort);

    //查询全部
    List<Channel> findAll(Integer id);

    //
    Optional<Channel> findById(int id);

    int save(Channel channel);

    int deleteById(int id);

    long count();

    Map<Integer, Channel> findMapByIds(Set<Integer> id);

    List<Channel> findAllById(Collection<Integer> id);


}
