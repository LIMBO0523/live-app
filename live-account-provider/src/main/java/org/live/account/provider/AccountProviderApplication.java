package org.live.account.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 12:02
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
public class AccountProviderApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(AccountProviderApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }
}

