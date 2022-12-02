package example;

import lombok.Data;

@Data
public class AuthContext {
  private boolean authorized;
  private String userName;
  private long userId;
}
