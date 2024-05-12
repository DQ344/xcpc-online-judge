package com.cqyt.evaluation;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qlh
 * @date 2024/05/01 20:11
 * @description 测评机模块
 */
@SpringBootApplication
public class EvaluationApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(EvaluationApplication.class);
        springApplication.setBannerMode(Banner.Mode.CONSOLE);
        springApplication.run(args);
    }
}
