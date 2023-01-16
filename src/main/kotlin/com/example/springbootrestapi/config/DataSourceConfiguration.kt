package com.example.springbootrestapi.config

import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource


@ComponentScan(basePackages = ["com.example.springbootrestapi.service"])
@MapperScan(
  basePackages = ["com.example.springbootrestapi.mapper"],
  sqlSessionFactoryRef = "sqlSessionFactory"
)
@Configuration
class DataSourceConfiguration {

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.hikari")
  fun dataSource(): DataSource {
    return DataSourceBuilder.create()
      .type(HikariDataSource::class.java)
      .build()
  }

  @Bean
  fun sqlSessionFactory(
    dataSource: DataSource,
    applicationContext: ApplicationContext
  ): SqlSessionFactory {
    val sessionFactory = SqlSessionFactoryBean()
    sessionFactory.setDataSource(dataSource)
    sessionFactory.setMapperLocations(
      *PathMatchingResourcePatternResolver()
        .getResources("classpath:mapper/*.xml")
    )
    return sessionFactory.`object`!!
  }

  @Bean
  fun sqlSession(
    sqlSessionFactory: SqlSessionFactory
  ): SqlSessionTemplate {
    return SqlSessionTemplate(sqlSessionFactory)
  }

  @Bean
  fun transactionManager(): DataSourceTransactionManager {
    return DataSourceTransactionManager(dataSource())
  }
}