package com.itaddr.demo.oauth.config;

import com.itaddr.demo.oauth.domain.UserVO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "custom")
public class OAuthWebmvcProperties {

    private List<UserVO> users;

    public List<UserVO> getUsers() {
        return users;
    }

    public void setUsers(List<UserVO> users) {
        this.users = users;
    }


}
