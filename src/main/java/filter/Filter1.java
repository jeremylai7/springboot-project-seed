package filter;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: laizc
 * @Date: Created in 9:19 2019-01-23
 */
@WebFilter(urlPatterns = "/*",filterName = "filter1")
public class Filter1 implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("doFilter");
    }

    @Override
    public void destroy() {
        System.out.println("destory");
    }
}
