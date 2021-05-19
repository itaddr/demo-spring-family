package com.itaddr.demo.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class OAuthWebmvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthWebmvcApplication.class, args);
    }

}
