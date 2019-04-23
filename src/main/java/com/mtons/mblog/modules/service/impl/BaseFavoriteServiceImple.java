package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.base.utils.BeanMapUtils;
import com.mtons.mblog.modules.data.FavoriteVO;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.entity.Favorite;
import com.mtons.mblog.modules.mapper.BaseFavoriteMapper;
import com.mtons.mblog.modules.mapper.basePostMapper;
import com.mtons.mblog.modules.service.BaseFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.*;

public class BaseFavoriteServiceImple implements BaseFavoriteService {

    @Autowired
    BaseFavoriteMapper baseFavoriteMapper;
    @Autowired
    private basePostMapper basePostMapper;

    @Override
    public Page<FavoriteVO> pagingByUserId(Pageable pageable, long userId) {
        Page<Favorite> page = baseFavoriteMapper.findAllByUserId(pageable, userId);

        List<FavoriteVO> rets = new ArrayList<>();
        Set<Long> postIds = new HashSet<>();
        for (Favorite po : page.getContent()) {
            rets.add(BeanMapUtils.copy(po));
            postIds.add(po.getPostId());
        }

        if (postIds.size() > 0) {
            Map<Long, PostVO> posts = basePostMapper.findMapByIds(postIds);
            for (FavoriteVO t : rets) {
                PostVO p = posts.get(t.getPostId());
                t.setPost(p);
            }
        }
        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    public void add(long userId, long postId) {
        Favorite po = baseFavoriteMapper.findByUserIdAndPostId(userId, postId);

        Assert.isNull(po, "您已经收藏过此文章");

        // 如果没有喜欢过, 则添加记录
        po = new Favorite();
        po.setUserId(userId);
        po.setPostId(postId);
        po.setCreated(new Date());

        baseFavoriteMapper.save(po);
    }

    @Override
    public void delete(long userId, long postId) {
        Favorite po = baseFavoriteMapper.findByUserIdAndPostId(userId, postId);
        Assert.notNull(po, "还没有喜欢过此文章");
        baseFavoriteMapper.delete(po);
    }

    @Override
    public void deleteByPostId(long postId) {
        int rows = baseFavoriteMapper.deleteByPostId(postId);
//        log.info("favoriteRepository delete {}", rows);
    }
}
