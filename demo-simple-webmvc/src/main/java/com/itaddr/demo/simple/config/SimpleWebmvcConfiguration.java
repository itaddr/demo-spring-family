package com.itaddr.demo.simple.config;

import com.itaddr.demo.simple.mvc.TokenInterceptorHandler;
import com.itaddr.demo.simple.domain.UserVO;
import com.itaddr.demo.simple.mvc.SimpleInterceptorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SimpleWebmvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private SimpleWebmvcProperties prop;

    @Bean(name = "userMap", value = "userMap")
    public Map<String, UserVO> userMap() {
        Map<String, UserVO> userMap = new HashMap<>();
        prop.getUsers().forEach(e -> userMap.put(e.getUsercode(), e));
        return userMap;
    }

    /**
     * 注册自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SimpleInterceptorHandler()).addPathPatterns("/**").order(0);
        registry.addInterceptor(new TokenInterceptorHandler()).addPathPatterns("/simple/**").order(1);
    }

    /**
     * spring boot会按照order值的大小，从小到大的顺序来依次过滤
     *
     * @return
     */
    /*@Bean
    @Order(2)
    public FilterRegistrationBean<AuthorizeFilter> configFilter() {
        FilterRegistrationBean<AuthorizeFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuthorizeFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("sessionFilter");
        //filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }*/

}
