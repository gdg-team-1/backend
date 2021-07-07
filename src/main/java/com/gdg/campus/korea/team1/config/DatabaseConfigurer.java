package com.gdg.campus.korea.team1.config;


import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan("com.gdg.campus.korea.team1.mapper")
public class DatabaseConfigurer {

  @Bean
  @ConfigurationProperties("spring.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    sessionFactory.setMapperLocations(resolver.getResources("classpath:/mapper/**/*.xml"));
    sessionFactory.setVfs(SpringBootVFS.class);
    sessionFactory.setTypeAliasesPackage("com.gdg.campus.korea.team1.model");
    SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
    if (sqlSessionFactory != null) {
      org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
      if (configuration != null) {
        configuration.setMapUnderscoreToCamelCase(true);
        return sessionFactory.getObject();
      }
    }
    return null;
  }

  @Bean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  @Bean
  public DataSourceTransactionManager transactionManager(){
    return new DataSourceTransactionManager(dataSource());
  }
}
