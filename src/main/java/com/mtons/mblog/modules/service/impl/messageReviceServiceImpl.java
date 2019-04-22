package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.base.utils.BeanMapUtils;
import com.mtons.mblog.modules.data.MessageVO;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.entity.Message;
import com.mtons.mblog.modules.mapper.UseRegisterMapper;
import com.mtons.mblog.modules.mapper.basePostMapper;
import com.mtons.mblog.modules.mapper.messageReviceMapper;
import com.mtons.mblog.modules.service.messageReviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.*;

@Service
public class messageReviceServiceImpl  implements messageReviceService {

    @Autowired
    messageReviceMapper messageReviceMapper;

    @Autowired
    UseRegisterMapper useRegisterMapper;

    @Autowired
    basePostMapper  basePostMapper;



    @Override
    public Page<Message> findAllByUserId(Pageable pageable, long userId) {
        Page<Message> page = messageReviceMapper.findAllByUserId(pageable, userId);
        List<MessageVO> rets = new ArrayList<>();

        Set<Long> postIds = new HashSet<>();
        Set<Long> fromUserIds = new HashSet<>();

        // 筛选
        page.getContent().forEach(po -> {
            MessageVO no = BeanMapUtils.copy(po);

            rets.add(no);

            if (no.getPostId() > 0) {
                postIds.add(no.getPostId());
            }
            if (no.getFromId() > 0) {
                fromUserIds.add(no.getFromId());
            }

        });

        // 加载
        Map<Long, PostVO> posts = basePostMapper.findMapByIds(postIds);
        Map<Long, UserVO> fromUsers = useRegisterMapper.findMapByIds(fromUserIds);

        rets.forEach(n -> {
            if (n.getPostId() > 0) {
                n.setPost(posts.get(n.getPostId()));
            }
            if (n.getFromId() > 0) {
                n.setFrom(fromUsers.get(n.getFromId()));
            }
        });
        return null;

//        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    public int countByUserIdAndStatus(long userId, int status) {
        return 0;
    }

    @Override
    public int updateReadedByUserId(Long uid) {
        return 0;
    }

    @Override
    public Page<MessageVO> pagingByUserId(Pageable pageable, long userId) {
        return null;
    }

    @Override
    public void send(MessageVO message) {

    }

    @Override
    public int unread4Me(long userId) {
        return   messageReviceMapper.updateReadedByUserId(userId);
    }

    @Override
    public void readed4Me(long userId) {
        messageReviceMapper.updateReadedByUserId(userId);
    }

    @Override
    public int deleteByPostId(long postId) {
        return messageReviceMapper.deleteByPostId(postId);
    }
}
