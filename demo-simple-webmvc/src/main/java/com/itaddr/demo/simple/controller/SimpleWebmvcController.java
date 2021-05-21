package com.itaddr.demo.simple.controller;

import com.itaddr.demo.simple.config.SimpleWebmvcProperties;
import com.itaddr.demo.simple.domain.ResultVO;
import com.itaddr.demo.simple.domain.UserVO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.POST})
@Controller
@RequestMapping("/simple")
public class SimpleWebmvcController {

    private final SimpleWebmvcProperties prop;

    private final Map<String, UserVO> userMap;

    public SimpleWebmvcController(SimpleWebmvcProperties prop, @Qualifier("userMap") Map<String, UserVO> userMap) {
        this.prop = prop;
        this.userMap = userMap;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String users(Model model) {
        model.addAttribute("title", "用户列表");
        model.addAttribute("users", prop.getUsers());
        // 转到待渲染模板，所有模板都在templates文件夹下，users指templates文件夹下的users文件夹下的list.html页面。
        return "users";
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultVO<UserVO> getUser(@RequestParam("access_token") String accessToken, @RequestParam("openid") String usercode) {
        return ResultVO.ok(userMap.get(usercode));
    }

    /*@ResponseBody*/
    @RequestMapping(value = "/entity", method = RequestMethod.GET)
    public ResponseEntity<String> simpleRes(@RequestParam("access_token") String accessToken, @RequestParam("openid") String usercode) {
        // body content
        String bodyString = "{\"code\":10001,\"msg\":\"success\"}";
        // write header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Vary", "Origin");
        headers.add("Content-Type", "application/json");
        headers.add("Date", new Date().toString());
        headers.add("Connection", "keep-alive");
        headers.add("Keep-Alive", "timeout=60");
        // write body
        return ResponseEntity.ok().headers(headers).body(bodyString);
    }

    @RequestMapping(value = "/testvoid", method = RequestMethod.GET)
    public void testvoid() {

    }

}
