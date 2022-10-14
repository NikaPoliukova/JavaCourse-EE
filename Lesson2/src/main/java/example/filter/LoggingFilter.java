package example.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        System.out.println("(Filter) Start. The caller IP is " + request.getLocalAddr());
        chain.doFilter(request, response);
        System.out.println("(Filter) Finish. ");
    }

    @Override
    public void destroy() {
    }
}
