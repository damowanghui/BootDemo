package com.heeexy.example;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author: heeexy
 * @description: SpringBoot启动类
 * @date: 2017/10/24 11:55
 */
@SpringBootApplication
@MapperScan("com.heeexy.example.dao")
@Slf4j
public class MyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MyApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
        log.info(" _______           _______  _______  _______  _______  _______ \n" +
                "(  ____ \\|\\     /|(  ____ \\(  ____ \\(  ____ \\(  ____ \\(  ____ \\\n" +
                "| (    \\/| )   ( || (    \\/| (    \\/| (    \\/| (    \\/| (    \\/\n" +
                "| (_____ | |   | || |      | |      | (__    | (_____ | (_____ \n" +
                "(_____  )| |   | || |      | |      |  __)   (_____  )(_____  )\n" +
                "      ) || |   | || |      | |      | (            ) |      ) |\n" +
                "/\\____) || (___) || (____/\\| (____/\\| (____/\\/\\____) |/\\____) |\n" +
                "\\_______)(_______)(_______/(_______/(_______/\\_______)\\_______)\n" +
                "                             ");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(MyApplication.class);
    }
}
