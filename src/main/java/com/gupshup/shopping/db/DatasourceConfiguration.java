package com.gupshup.shopping.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
public class DatasourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.shopping")
    public DataSourceProperties shoppingDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource shoppingDataSource() {
        return shoppingDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }

    @Bean
    public JdbcTemplate shoppingJdbcTemplate(@Qualifier("shoppingDataSource") DataSource dataSource) {
       return new JdbcTemplate(dataSource);
    }

}