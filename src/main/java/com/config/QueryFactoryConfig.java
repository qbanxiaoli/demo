package com.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-11-11 00:34
 */
@Configuration
public class QueryFactoryConfig {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public SQLQueryFactory sqlQueryFactory(DataSource dataSource) {
        SQLTemplates template = MySQLTemplates.builder().printSchema().build();
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(template);
        // 启用文字的直接序列化，打印出完整的sql语句
        configuration.setUseLiterals(true);
        return new SQLQueryFactory(configuration, dataSource);
    }

}
