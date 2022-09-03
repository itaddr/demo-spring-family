package com.itaddr.demo.simple.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName DeferredResultController
 * @Description TODO
 * @Author MyPC
 * @Date 2022/8/4 17:01
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/poll")
public class DeferredResultController {

    private final ConcurrentMap<String, List<DeferredResult<Object>>> features = new ConcurrentHashMap<>();

    @RequestMapping("/feature/{topic}")
    public DeferredResult<?> feature(@PathVariable("topic") String topic) {
        log.info("Request received");
        final DeferredResult<Object> feature = new DeferredResult<>();
        // 当deferredResult完成时（不论是超时还是异常还是正常完成），移除watchRequests中相应的watch key
        feature.onCompletion(() -> {
            log.info("remove topic: {}", topic);
            features.remove(topic, feature);
        });
        features.computeIfAbsent(topic, (key) -> new ArrayList<>()).add(feature);
        log.info("Servlet thread released");
        return feature;
    }

    @RequestMapping("/promise/{topic}")
    public String promise(@PathVariable("topic") String topic) {
        if (features.containsKey(topic)) {
            final List<DeferredResult<Object>> featureList = features.get(topic);
            long time = System.currentTimeMillis();
            //通知所有watch这个namespace变更的长轮训配置变更结果
            for (DeferredResult<Object> feature : featureList) {
                feature.setResult(topic + " changed: " + time);
            }
        }
        return "success";
    }

}
