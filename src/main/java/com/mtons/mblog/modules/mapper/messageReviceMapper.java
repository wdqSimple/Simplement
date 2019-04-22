package com.mtons.mblog.modules.mapper;

import com.mtons.mblog.modules.data.MessageVO;
import com.mtons.mblog.modules.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import java.awt.print.Pageable;

@Mapper
public interface messageReviceMapper {

    Page<Message> findAllByUserId(Pageable pageable, long userId);

    /**
     * 查询我的未读消息
     * @param userId
     * @return
     */
    int countByUserIdAndStatus(long userId, int status);

    /**
     * 标记我的消息为已读
     */
    int updateReadedByUserId(@Param("uid") Long uid);


    Page<MessageVO> pagingByUserId(Pageable pageable, long userId);

    /**
     * 发送通知
     * @param message
     */
    void send(MessageVO message);

    /**
     * 未读消息数量
     * @param userId
     * @return
     */
    int unread4Me(long userId);

    /**
     * 标记为已读
     * @param userId
     */
    void readed4Me(long userId);

    /**
     * 根据文章ID清理消息
     * @param postId
     * @return
     */
    int deleteByPostId(long postId);



}
