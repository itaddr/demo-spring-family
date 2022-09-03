package com.itaddr.demo.simple;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

public class ResponseEntityTest {

    @Test
    public void test01() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Vary", "Origin");
        headers.add("Vary", "Origin");
        headers.add("Vary", "Origin");
        headers.add("Content-Type", "application/json");
        headers.add("Transfer-Encoding", "chunked");
        headers.add("Date", new Date().toString());
        headers.add("Connection", "keep-alive");
        headers.add("Keep-Alive", "timeout=60");

        ResponseEntity<String> response = ResponseEntity.ok()
                .headers(headers)
                .body("{\"code\":10003,\"msg\":\"not found access_token\"}");

        System.out.println(response.toString());


    }

    @Test
    public void test02() {
        System.out.println(new BCryptPasswordEncoder().encode("NaCos@2022.8.24"));
    }

}
