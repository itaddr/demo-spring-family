package com.itaddr.demo.simple;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 马嘉祺
 * @Date 2020/12/21 0021 22 04
 * @Description <p></p>
 */
@Slf4j
@RestController
public class ConsumerController {
    
    private DiscoveryClient discoveryClient;
    
    private ProviderClient providerClient;
    
    public ConsumerController(DiscoveryClient discoveryClient, ProviderClient providerClient) {
        this.discoveryClient = discoveryClient;
        this.providerClient = providerClient;
    }
    
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String health() {
        return "OK";
    }
    
    @GetMapping("/service")
    public Object getClient() {
        return discoveryClient.getServices();
    }
    
    @GetMapping("/instance")
    public List<ServiceInstance> getInstance(String instanceId) {
        return discoveryClient.getInstances(instanceId);
    }
    
    @GetMapping("/ping")
    public String ping() {
        return new JSONObject().fluentPut("success", true).fluentPut("data", providerClient.ping()).toJSONString();
    }
    
}
