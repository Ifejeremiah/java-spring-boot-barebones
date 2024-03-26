package com.ifejeremiah.config;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Value("${spring.datasource.driverClassName}")
    private String jdbcDriverClassName;
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String jdbcUsername;
    @Value("${spring.datasource.password}")
    private String jdbcPassword;
    @Value("${spring.datasource.maximum-pool-size}")
    private int maxPoolSize;

    @Bean
    public DataSource DataSource() {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(jdbcDriverClassName);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        dataSource.setMaximumPoolSize(maxPoolSize);
        dataSource.setConnectionTestQuery("SELECT 1");

        dataSource.addDataSourceProperty("cachePrepStmts", true);
        dataSource.addDataSourceProperty("prepStmtCacheSize", 25000);
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 20048);
        dataSource.addDataSourceProperty("useServerPrepStmts", true);
        dataSource.addDataSourceProperty("initializationFailFast", true);
        dataSource.setPoolName("Connection pool");

        return dataSource;
    }

    @Bean(initMethod = "migrate")
    @Autowired
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .baselineVersion("1")
                .baselineOnMigrate(true)
                .validateOnMigrate(false)
                .outOfOrder(true).load();
    }
}
