package com.example.lessonSpringBoot.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.awt.*;

public interface ImageRepository extends Repository<Image, Long> {

  @Modifying
  @Query("insert into image (image_name, user_id) values (:imageName, :userId)")
  void setNewImage(@Param("imageName") String imageName, @Param("userId") long userId);

  @Modifying
  @Query("update image set image_name=:imageName where user_id=:userId")
  void updateImage(@Param("imageName") String imageName, @Param("userId") long userId);

  @Query("select image_name from image where user_id=:userId")
  String getImageNameByUserId(@Param("userId") long userId);

  @Modifying
  @Query("delete image where user_id=:userId")
  void deleteImage( @Param("userId") long userId);
}

