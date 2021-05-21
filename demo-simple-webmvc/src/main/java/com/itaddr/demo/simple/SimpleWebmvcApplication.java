package com.itaddr.demo.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SimpleWebmvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleWebmvcApplication.class, args);
    }

}
