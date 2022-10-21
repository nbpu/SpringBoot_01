package com.example.demo.dataSourceConfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.demo.utils.EncryptAndDecryptStr;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = {"com.example.demo.*.dao"},sqlSessionTemplateRef = "mysqlSqlSessionTemplate")
public class DataSourceConfig {
    private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean(name = "mysqlInnetDataSource")
    public DataSource druid(){
        try{
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
            String userName = EncryptAndDecryptStr.decryptStr("username",dataSourceProperties.getUsername());
            String password = EncryptAndDecryptStr.decryptStr("password",dataSourceProperties.getPassword());
            logger.info("-----------解密后的username："+userName);
            logger.info("-----------解密后的password："+password);

            druidDataSource.setUrl(dataSourceProperties.getUrl());
            druidDataSource.setUsername(userName);
            druidDataSource.setPassword(password);
            logger.info("-----------创建数据源成功");
            return druidDataSource;
        }catch (Exception e){
            logger.error("----------创建数据源出错，异常{}" + e);
            return null;
        }
    }

    @Bean(name = "mysqlSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("mysqlInnetDataSource") DataSource dataSource){
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSource);
            bean.setConfigLocation(new PathMatchingResourcePatternResolver()
                    .getResource("classpath:mybatis-config.xml"));
            bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                            .getResources("classpath:mapper/*.xml"));
            return bean.getObject();
        }catch (Exception e){
            logger.error("----------SqlSessionFactory，异常{}" + e);
            return null;
        }
    }
    @Bean(name = "TransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("mysqlInnetDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mysqlSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
