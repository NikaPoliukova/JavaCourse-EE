package com.example.messages.repository;


import com.example.messages.dto.MessageDto;
import com.example.messages.model.Message;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends Repository<MessageDto, Long> {
  @Modifying
  @Query("insert into messages (source_user_id, target_user_id, message_content) " +
      "VALUES (:sourceUserId, :targetUserId, :message_content)")
  void saveMessage(@Param("sourceUserId") long sourceUserId, @Param("targetUserId") long targetUserId,
                   @Param("message_content") String message_content);

  @Query("select * from messages where (source_user_id = :sourceUserId or source_user_id = :targetUserId) " +
      "and (target_user_id = :sourceUserId or target_user_id = :targetUserId) order by created_at ASC")
  List<Message> getMessagesByFriendId(@Param("sourceUserId") long sourceUserId,
                                      @Param("targetUserId") long targetUserId);
}