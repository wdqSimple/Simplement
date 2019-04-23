package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.base.utils.BeanMapUtils;
import com.mtons.mblog.modules.data.CommentVO;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.entity.Comment;
import com.mtons.mblog.modules.mapper.BaseCommentMapper;
import com.mtons.mblog.modules.mapper.UseRegisterMapper;
import com.mtons.mblog.modules.mapper.basePostMapper;
import com.mtons.mblog.modules.service.BaseCommentService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

@Service(value = "BaseCommentService")
public class BaseCommentServiceImpl implements BaseCommentService {

    @Autowired
    BaseCommentMapper baseCommentMapper;
    @Autowired
    UseRegisterMapper useRegisterMapper;
    @Autowired
    basePostMapper basePostMapper;
    @Autowired
    BaseCommentService baseCommentService;


    @Override
    public Page<CommentVO> paging4Admin(Pageable pageable) {
        Page<Comment> page = baseCommentMapper.findAll(pageable);
        List<CommentVO> rets = new ArrayList<>();

        HashSet<Long> uids = new HashSet<>();

        page.getContent().forEach(po -> {
            uids.add(po.getAuthorId());
            rets.add(BeanMapUtils.copy(po));
        });

        buildUsers(rets, uids);

        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    public Page<CommentVO> pagingByAuthorId(Pageable pageable, long authorId) {
        Page<Comment> page = baseCommentMapper.findAllByAuthorId(pageable, authorId);

        List<CommentVO> rets = new ArrayList<>();
        Set<Long> parentIds = new HashSet<>();
        Set<Long> uids = new HashSet<>();
        Set<Long> postIds = new HashSet<>();

        page.getContent().forEach(po -> {
            CommentVO c = BeanMapUtils.copy(po);

            if (c.getPid() > 0) {
                parentIds.add(c.getPid());
            }
            uids.add(c.getAuthorId());
            postIds.add(c.getPostId());

            rets.add(c);
        });

        // 加载父节点
        buildParent(rets, parentIds);

        buildUsers(rets, uids);
        buildPosts(rets, postIds);

        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    public Page<CommentVO> pagingByPostId(Pageable pageable, long postId) {
        Page<Comment> page = baseCommentMapper.findAllByPostId(pageable, postId);
        List<CommentVO> rets = new ArrayList<>();
        Set<Long> parentIds = new HashSet<>();
        Set<Long> uids = new HashSet<>();

        page.getContent().forEach(po -> {
            CommentVO c = BeanMapUtils.copy(po);

            if (c.getPid() > 0) {
                parentIds.add(c.getPid());
            }
            uids.add(c.getAuthorId());

            rets.add(c);
        });

        // 加载父节点
        buildParent(rets, parentIds);

        buildUsers(rets, uids);

        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    public List<CommentVO> findLatestComments(int maxResults) {
        Pageable pageable = PageRequest.of(0, maxResults, new Sort(Sort.Direction.DESC, "id"));
        Page<Comment> page = baseCommentMapper.findAll(pageable);
        List<CommentVO> rets = new ArrayList<>();

        HashSet<Long> uids = new HashSet<>();

        page.getContent().forEach(po -> {
            uids.add(po.getAuthorId());
            rets.add(BeanMapUtils.copy(po));
        });

        buildUsers(rets, uids);
        return rets;
    }

    @Override
    public Map<Long, CommentVO> findByIds(Set<Long> ids) {
        return null;
//        return baseCommentMapper.findByIdS(ids).orElse(null);

    }

    @Override
    public Comment findById(long id) {
        return null;
    }

    @Override
    public long post(CommentVO comment) {
        Comment po = new Comment();

        po.setAuthorId(comment.getAuthorId());
        po.setPostId(comment.getPostId());
        po.setContent(comment.getContent());
        po.setCreated(new Date());
        po.setPid(comment.getPid());
        baseCommentMapper.save(po);

//        userEventService.identityComment(comment.getAuthorId(), true);
        return po.getId();
    }

    @Override
    public void delete(List<Long> ids) {
        List<Comment> list = baseCommentMapper.removeByIdIn(ids);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(po -> {
//                userEventService.identityComment(po.getAuthorId(), false);
            });
        }
    }

    @Override
    public void delete(long id, long authorId) {
        Optional<Comment> optional = baseCommentMapper.findById(id);
        if (optional.isPresent()) {
            Comment po = optional.get();
            // 判断文章是否属于当前登录用户
            Assert.isTrue(po.getAuthorId() == authorId, "认证失败");
            baseCommentMapper.deleteById(id);
//            userEventService.identityComment(authorId, false);
        }
    }

    @Override
    public void deleteByPostId(long postId) {
        List<Comment> list = baseCommentMapper.removeByPostId(postId);
        if (CollectionUtils.isNotEmpty(list)) {
            Set<Long> userIds = new HashSet<>();
            list.forEach(n -> userIds.add(n.getAuthorId()));
            // userEventService.identityComment(userIds, false);

        }
    }

    @Override
    public long count() {
        return baseCommentMapper.count();
    }

    @Override
    public long countByAuthorIdAndPostId(long authorId, long postId) {
        return 0;
    }

    private void buildUsers(Collection<CommentVO> posts, Set<Long> uids) {
        Map<Long, UserVO> userMap = useRegisterMapper.findMapByIds(uids);

        posts.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
    }

    private void buildPosts(Collection<CommentVO> comments, Set<Long> postIds) {
        Map<Long, PostVO> postMap = basePostMapper.findMapByIds(postIds);
        comments.forEach(p -> p.setPost(postMap.get(p.getPostId())));
    }

    private void buildParent(Collection<CommentVO> comments, Set<Long> parentIds) {
        if (!parentIds.isEmpty()) {
            Map<Long, CommentVO> pm = findByIds(parentIds);

            comments.forEach(c -> {
                if (c.getPid() > 0) {
                    c.setParent(pm.get(c.getPid()));
                }
            });
        }
    }
}
