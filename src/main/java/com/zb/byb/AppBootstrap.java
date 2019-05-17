package com.zb.byb;

import com.zb.framework.rest.ZbRestProject;
import com.zb.framework.swagger.EnableSwagger2Doc;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@EnableScheduling
@SpringBootApplication
@EnableSwagger2Doc
@ZbRestProject
public class AppBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AppBootstrap.class).run(args);
    }

    @Bean
    public TaskScheduler scheduledExecutorService() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(8);
        return scheduler;
    }
}
