package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

// 开启定时任务支持
@EnableScheduling
// 自动插入和更新时间
@EnableJpaAuditing
@SpringBootApplication
public class QuantizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuantizationApplication.class, args);
    }

}
