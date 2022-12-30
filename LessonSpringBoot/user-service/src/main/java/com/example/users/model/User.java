package com.example.users.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;


@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
  @Id
  @Column("user_id")
  private Long userId;

  @Column("username")
  private String userName;

  private String password;

  @Column("created_at")
  private Date createdDate;


  public User(Long userId, String userName) {
    this.userId = userId;
    this.userName = userName;
  }

  public User(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  public User(String userName) {
    this.userName = userName;
  }
}
