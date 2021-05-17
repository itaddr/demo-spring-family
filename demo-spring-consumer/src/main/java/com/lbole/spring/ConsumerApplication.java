package com.lbole.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @Author 马嘉祺
 * @Date 2020/12/11 0011 11 40
 * @Description <p></p>
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ConsumerApplication {
    
    static {
        URL baseUrl = ConsumerApplication.class.getClassLoader().getResource("");
        Path baseDir = new File(Objects.requireNonNull(baseUrl).getPath()).toPath();
        setIfAbsent("base.dir", baseDir.toString());
        setIfAbsent("config.dir", baseDir.resolve("config").toString());
        setIfAbsent("logs.output", "console");
        setIfAbsent("logs.dir", baseDir.resolve("logs").toString());
        setIfAbsent("logs.namespace", "service");
    }
    
    public static String setIfAbsent(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (null == value || 0 == value.length()) {
            value = defaultValue;
            System.setProperty(key, value);
        }
        return value;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
        log.info("================ start success ================");
    }
    
}
