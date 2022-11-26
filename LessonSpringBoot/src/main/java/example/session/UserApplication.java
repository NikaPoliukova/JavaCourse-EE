package example.session;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserApplication {

  protected UserApplication() {//без конструктора ругается(Utils-классы не должны
    // иметь публичных конструкторов или конструктор по умолчанию.)
  }

  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }
}
