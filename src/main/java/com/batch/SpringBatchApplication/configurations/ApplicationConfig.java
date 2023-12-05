package com.batch.SpringBatchApplication.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
public class ApplicationConfig {
    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("datasource.driver.class.name")));
        dataSource.setUrl(env.getProperty("datasource.url.name"));
        dataSource.setUsername(env.getProperty("datasource.username"));
        dataSource.setPassword(env.getProperty("datasource.pass"));

        Properties connectionProperties = new Properties();
        connectionProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        dataSource.setConnectionProperties(connectionProperties);

        return dataSource;
    }
}
