package com.zhx.contentcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 封心
 */
@SpringBootApplication
@MapperScan("com.zhx")
public class ContentcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentcenterApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
