package com.itaddr.demo.simple.domain;

import lombok.Data;

@Data
public class UserVO {

    private String usercode;

    private String password;

    private String username;

    private Integer age;

    private Character sex;

    private String address;

}
