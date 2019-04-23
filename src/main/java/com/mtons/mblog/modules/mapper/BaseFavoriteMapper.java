package com.mtons.mblog.modules.mapper;

import com.mtons.mblog.modules.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Mapper
public interface BaseFavoriteMapper {

    //根据用户ID和文章ID查询当前收藏
    Favorite findByUserIdAndPostId(long userId, long postId);

    //根据用户ID查询全部收藏信息
    Page<Favorite> findAllByUserId(Pageable pageable, long userId);

    //删除当前文章ID
    int deleteByPostId(long postId);

    //保存收藏
    Boolean save(Favorite favorite);

    //删除收藏
    Boolean delete(Favorite favorite);
}
