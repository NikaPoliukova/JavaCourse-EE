package example.config;


import example.AuthContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;



@Configuration
public class SessionConfig {

  @Bean
  @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
  public AuthContext authContext() {
    return new AuthContext();
  }
}
