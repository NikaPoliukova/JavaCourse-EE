package example.interceptor;

import example.AuthContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

  private final AuthContext authContext;

  @SneakyThrows
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler
  ) {
    if (authContext.isAuthorized()) {
      return true;
    }
    response.sendRedirect("login");
    return false;
  }
}
