package filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: laizc
 * @Date: Created in 10:39 2019-01-23
 */

public class FilterConfig {

    public FilterRegistrationBean registFilter(){
        FilterRegistrationBean registeration = new FilterRegistrationBean();
        registeration.setFilter(new Filter1());
        registeration.addUrlPatterns("/*");
        registeration.setName("Filter1");
        registeration.setOrder(1);
        return registeration;
    }
}
