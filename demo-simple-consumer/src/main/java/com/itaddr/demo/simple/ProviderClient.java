package com.itaddr.demo.simple;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author 马嘉祺
 * @Date 2020/12/21 0021 22 14
 * @Description <p></p>
 */
@FeignClient(name = "spring-provider", fallback = ProviderClientFallback.class)
public interface ProviderClient {
    
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    String ping();
    
}
