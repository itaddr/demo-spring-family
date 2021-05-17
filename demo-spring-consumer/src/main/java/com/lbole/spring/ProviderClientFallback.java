package com.lbole.spring;

import org.springframework.stereotype.Service;

/**
 * @Author 马嘉祺
 * @Date 2020/12/21 0021 22 15
 * @Description <p></p>
 */
@Service
public class ProviderClientFallback implements ProviderClient {
    
    @Override
    public String ping() {
        return "provider error";
    }
    
}
