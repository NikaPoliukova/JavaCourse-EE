package example.filter;

import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;


@WebFilter(value = "/users")
public class SecurityFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {
    final HttpServletRequest httpRequest = (HttpServletRequest) request;
    boolean isAuthorized = httpRequest.getSession().getAttribute("username") != null;
    if (!isAuthorized) {
      request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
      return;
    }
    chain.doFilter(request, response);
  }
}
