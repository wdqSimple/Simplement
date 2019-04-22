package com.mtons.mblog.modules.mapper;

import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.entity.Post;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

//文章基本信息
@Mapper
public interface basePostMapper {

    //发布文章
    Boolean SavePost(Post post);

    //删除文章
    Boolean DelPostById(@Param("id") long id);

    //查询文章信息

    List<Post> listPost(@Param("id") long id);

    //修改文章
    List<Post> EditPostById(@Param("id") long id);


    //查询当前作者下面创作的信息
    Page<Post> findAllByAuthorId(Pageable pageable, long authorId);

    //更新文章评论
    void updateComments(@Param("id") long id, @Param("increment") int increment);

    //更新收藏
    void updateFavors(@Param("id") long id, @Param("increment") int increment);

    //更新文章
    void updateViews(@Param("id") long id, @Param("increment") int increment);

    //查询文章
    Map<Long, PostVO> findMapByIds(Set<Long> ids);

    Page<Post> findAll();
}
