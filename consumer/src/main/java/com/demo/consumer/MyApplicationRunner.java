package com.demo.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Autowired
    RestTemplate restTemplate;

    public void run(ApplicationArguments applicationArguments) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> {
                Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
                    Map entityMap = new HashMap();
                    entityMap.put("a", "1");
                    HttpHeaders requestHeaders = new HttpHeaders();
                    HttpEntity<Map> requestEntity = new HttpEntity<>(entityMap, requestHeaders);
                    String jsonResult = restTemplate.postForObject("http://localhost/demo/test", requestEntity, String.class);
                    System.out.println(jsonResult);
                }, 0, 1000 + (int) (Math.random() * 2000), TimeUnit.MILLISECONDS);
            });
        }

    }

}
