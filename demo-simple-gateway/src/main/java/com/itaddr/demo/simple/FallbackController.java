package com.itaddr.demo.simple;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 马嘉祺
 * @Date 2020/12/22 0022 17 42
 * @Description <p></p>
 */
@RestController
public class FallbackController {
    
    /**
     * @ClassName FallbackController
     * @Desc TODO 网关断路器
     * @Date 2019/6/23 19:35
     * @Version 1.0
     */
    @RequestMapping("/fallback3")
    public String fallback3() {
        return "I'm Spring Cloud Gateway fallback3.";
    }
    
    @RequestMapping("/defaultFallback")
    public String defaultFallback() {
        return "I'm Spring Cloud Gateway defaultFallback.";
    }
    
}
