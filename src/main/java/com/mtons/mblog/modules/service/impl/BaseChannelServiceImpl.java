package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.modules.entity.Channel;
import com.mtons.mblog.modules.mapper.ChannelMapper;
import com.mtons.mblog.modules.service.BaseChannelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.*;

public class BaseChannelServiceImpl implements BaseChannelService {

    @Autowired
    ChannelMapper channelMapper;

    @Override
    public List<Channel> findAll(int status) {
        Sort sort = Sort.by(Sort.Direction.DESC, "weight", "id");
        List<Channel> list;
        if (status > Consts.IGNORE) {
            list = channelMapper.findAllByStatus(status, sort);
        } else {
            list = channelMapper.findAll(sort);
        }
        return list;
    }

    @Override
    public Map<Integer, Channel> findMapByIds(Collection<Integer> ids) {
        List<Channel> list = channelMapper.findAllById(ids);
        Map<Integer, Channel> rets = new HashMap<>();
        list.forEach(po -> rets.put(po.getId(), po));
        return rets;
    }

    @Override
    public Channel getById(int id) {
        return channelMapper.findById(id).get();
    }

    @Override
    public void update(Channel channel) {
        Optional<Channel> optional = channelMapper.findById(channel.getId());
        Channel po = optional.orElse(new Channel());
        BeanUtils.copyProperties(channel, po);
        channelMapper.save(po);

    }

    @Override
    public void updateWeight(int id, int weighted) {
        Channel po = channelMapper.findById(id).get();

        int max = Consts.ZERO;
        if (Consts.FEATURED_ACTIVE == weighted) {
            max = channelMapper.maxWeight() + 1;
        }
        po.setWeight(max);
        channelMapper.save(po);
    }

    @Override
    public void delete(int id) {
        channelMapper.deleteById(id);

    }

    @Override
    public long count() {
        return channelMapper.count();
    }
}
