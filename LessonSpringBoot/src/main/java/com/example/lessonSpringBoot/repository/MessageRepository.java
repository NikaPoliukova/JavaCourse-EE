package com.example.lessonSpringBoot.repository;

import com.example.lessonSpringBoot.dto.MessageDto;
import com.example.lessonSpringBoot.model.Message;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends Repository<MessageDto, Long> {

  void sendMessage(@Param("myUserId") Long myUserId,@Param("friendUserId") Long friendUserId,
                   @Param("text") String text);

  List<Message> getMessagesByFriend(@Param("myUserId")Long myUserId, @Param("friendUserId") Long friendUserId);


}