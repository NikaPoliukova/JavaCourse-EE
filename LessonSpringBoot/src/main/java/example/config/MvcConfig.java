package example.config;


import example.interceptor.AuthInterceptor;

import example.AuthContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

  private final AuthContext authContext;

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/users").setViewName("users");
    registry.addViewController("/friends").setViewName("friends");
    registry.addViewController("/incoming_friend_requests").setViewName("incoming_friend_requests");
    registry.addViewController("/login").setViewName("login");
    registry.addViewController("/outgoing_friend_requests").setViewName("outgoing_friend_requests");
    registry.addViewController("/registration").setViewName("registration");
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new MappedInterceptor(new String[]{"/users"}, new AuthInterceptor(authContext)));
  }
}
