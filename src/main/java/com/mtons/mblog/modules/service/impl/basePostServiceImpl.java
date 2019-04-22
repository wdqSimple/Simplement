package com.mtons.mblog.modules.service.impl;


import com.mtons.mblog.base.lang.Consts;
import com.mtons.mblog.base.utils.BeanMapUtils;
import com.mtons.mblog.modules.data.PostVO;
import com.mtons.mblog.modules.data.UserVO;
import com.mtons.mblog.modules.entity.Channel;
import com.mtons.mblog.modules.entity.Post;
import com.mtons.mblog.modules.event.PostUpdateEvent;
import com.mtons.mblog.modules.mapper.ChannelMapper;
import com.mtons.mblog.modules.mapper.UseRegisterMapper;
import com.mtons.mblog.modules.mapper.basePostMapper;
import com.mtons.mblog.modules.service.basePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class basePostServiceImpl implements basePostService {

    @Autowired
    basePostMapper basePostMapper;

    @Autowired
    UseRegisterMapper useRegisterMapper;

    @Autowired
    ChannelMapper channelMapper;


    @Override
    public Page<PostVO> paging(Pageable pageable, int channelId, Set<Integer> excludeChannelIds) {
        return null;
    }

    @Override
    public Page<PostVO> paging4Admin(Pageable pageable, int channelId, String title) {
        return null;
    }

    @Override
    public Page<PostVO> pagingByAuthorId(Pageable pageable, long userId) {
        Page<Post> page = basePostMapper.findAllByAuthorId(pageable, userId);
        return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
    }

    @Override
    public List<PostVO> findLatestPosts(int maxResults) {
        return null;
    }

    @Override
    public List<PostVO> findHottestPosts(int maxResults) {
        return null;
    }

    @Override
    public Map<Long, PostVO> findMapByIds(Set<Long> ids) {
        return null;
    }

    @Override
    public long post(PostVO post) {
        return 0;
    }

    @Override
    public PostVO get(long id) {
        return null;
    }

    @Override
    public void update(PostVO p) {

    }

    @Override
    public void updateFeatured(long id, int featured) {

    }

    @Override
    public void updateWeight(long id, int weighted) {

    }

    @Override
    public void delete(long id, long authorId) {

    }

    @Override
    public void delete(Collection<Long> ids) {

    }

    @Override
    public void identityViews(long id) {

    }

    @Override
    public void identityComments(long id) {

    }

    @Override
    public void favor(long userId, long postId) {

    }

    @Override
    public void unfavor(long userId, long postId) {

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<Post> find(String orderBy, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, orderBy));
        Set<Integer> excludeChannelIds = new HashSet<>();
        List<Channel> channels = channelMapper.findAll(Consts.STATUS_CLOSED);
        if (channels != null) {
            channels.forEach((c) -> excludeChannelIds.add(c.getId()));
        }
//        Page<Post> page = basePostMapper.findAll((root, query, builder) -> {
//            Predicate predicate = builder.conjunction();
//            if (excludeChannelIds.size() > 0) {
//                predicate.getExpressions().add(
//                        builder.not(root.get("channelId").in(excludeChannelIds)));
//            }
//            return predicate;
//        }, pageable);
//        return page.getContent();
        return null;
    }

    @Override
    public List<PostVO> toPosts(List<Post> posts) {
        List<PostVO> rets = new ArrayList<>();

        HashSet<Long> uids = new HashSet<>();
        HashSet<Integer> groupIds = new HashSet<>();

        posts.forEach(po -> {
            uids.add(po.getAuthorId());
            groupIds.add(po.getChannelId());
            rets.add(BeanMapUtils.copy(po));
        });
        // 加载用户信息
        buildUsers(rets, uids);
        buildGroups(rets, groupIds);
        return rets;
    }

    @Override
    public void buildUsers(Collection<PostVO> posts, Set<Long> uids) {
        Map<Long, UserVO> userMap = useRegisterMapper.findMapByIds(uids);
        posts.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
    }

    @Override
    public void buildGroups(Collection<PostVO> posts, Set<Integer> groupIds) {
        Map<Integer, Channel> map = channelMapper.findMapByIds(groupIds);
        posts.forEach(p -> p.setChannel(map.get(p.getChannelId())));
    }

    @Override
    public void onPushEvent(Post post, int action) {
    }

    @Override
    public Page<Post> findAll() {
        return null;
    }
}
