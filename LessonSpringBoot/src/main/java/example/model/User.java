package example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id")
 // @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
  Long userId;

  @Column(name = "username")
  String userName;

  @Column(name = "password")
  String password;

  @Column(name = "created_at", insertable = false)
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
