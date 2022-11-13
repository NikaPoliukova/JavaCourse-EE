package example.model;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
@NoArgsConstructor
public class User {
  Long userId;
  String userName;
  String password;
  Date createdDate;

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
