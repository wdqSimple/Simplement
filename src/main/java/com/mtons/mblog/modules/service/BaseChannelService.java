package com.mtons.mblog.modules.service;

import com.mtons.mblog.modules.entity.Channel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseChannelService {

    List<Channel> findAll(int status);

    Map<Integer, Channel> findMapByIds(Collection<Integer> ids);

    Channel getById(int id);

    void update(Channel channel);

    void updateWeight(int id, int weighted);

    void delete(int id);

    long count();
}
