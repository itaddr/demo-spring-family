package com.itaddr.demo.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;

/**
 * 参考：https://blog.csdn.net/qq_41402200/article/details/94333830
 * https://www.cnblogs.com/crazymakercircle/p/11704077.html
 *
 * @Author 马嘉祺
 * @Date 2020/12/22 0022 17 11
 * @Description <p></p>
 */
@Slf4j
@SpringBootApplication
public class SimpleGatewayApplication {
    
    static {
        URL baseUrl = SimpleGatewayApplication.class.getClassLoader().getResource("");
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
        SpringApplication.run(SimpleGatewayApplication.class, args);
        log.info("================ start success ================");
    }
    
    //@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route", r -> r.path("/csdn")
                        .uri("https://blog.csdn.net"))
                .build();
    }
    
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
    
}
