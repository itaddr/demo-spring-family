package com.lbole.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @Author 马嘉祺
 * @Date 2020/12/11 0011 11 26
 * @Description <p></p>
 */
@Slf4j
@RestController
public class ProviderController {
    
    private final String hostName = System.getenv("HOSTNAME");
    
    /**
     * 探针检查响应类
     *
     * @return
     */
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String health() {
        return "OK";
    }
    
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        log.info("ping of {}", hostName);
        return hostName;
    }
    
    /**
     * 返回hostname
     *
     * @return 当前应用所在容器的hostname.
     */
    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public String getName() {
        return String.format("%s, %s", hostName, LocalDateTime.now().toString());
    }
    
}
