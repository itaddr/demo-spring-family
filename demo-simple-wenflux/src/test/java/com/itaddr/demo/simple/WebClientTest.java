package com.itaddr.demo.simple;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

public class WebClientTest {


    public void test01() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8080");   // 1
        Mono<String> resp = webClient
                .get().uri("/hello") // 2
                .retrieve() // 3
                .bodyToMono(String.class);  // 4

        resp.subscribe(System.out::println);    // 5
        TimeUnit.SECONDS.sleep(1);  // 6

    }

}
