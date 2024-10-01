package org.idea.live.framework.datasource.starter.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @Author LIMBO0523
 * @Date 2024/10/1 16:02
 */
@Configuration
public class SharingJDBCDatasourceAutoInitConnectionConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SharingJDBCDatasourceAutoInitConnectionConfig.class);

    @Bean
    public ApplicationRunner runner(DataSource dataSource) {
        return args -> {
            LOGGER.info(" ==================  [ShardingJdbcDatasourceAutoInitConnectionConfig] dataSource: {}", dataSource);
            //手动触发下连接池的连接创建
            Connection connection = dataSource.getConnection();
        };
    }
}
