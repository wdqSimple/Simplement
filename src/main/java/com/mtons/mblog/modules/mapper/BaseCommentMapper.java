package com.mtons.mblog.modules.mapper;

import com.mtons.mblog.modules.data.CommentVO;
import com.mtons.mblog.modules.entity.Comment;
import com.sun.mail.imap.protocol.ID;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mapper
public interface BaseCommentMapper {

    //查询全部
    Page<Comment> findAll(Pageable pageable);

    //查询根据文章ID
    Page<Comment> findAllByPostId(Pageable pageable, long postId);

    //查询根据作者ID
    Page<Comment> findAllByAuthorId(Pageable pageable, long authorId);

    //根据ID删除
    List<Comment> removeByIdIn(Collection<Long> ids);

    //根据文章ID删除
    List<Comment> removeByPostId(long postId);

    //根据文章和作者ID查询总数
    long countByAuthorIdAndPostId(long authorId, long postId);

    //查询全部信息
    List<Comment> findAll();

    Optional<Comment> findById(long id);

    List<Comment> findByIdS(Set<Long> id);

    /**
     * 发表评论
     *
     * @param comment
     * @return
     */
    long post(CommentVO comment);

    void delete(List<Long> ids);

    void delete(long id, long authorId);

    void deleteByPostId(long postId);

    long count();

    Boolean deleteById(Long id);

    Boolean save(Comment comments);

}
