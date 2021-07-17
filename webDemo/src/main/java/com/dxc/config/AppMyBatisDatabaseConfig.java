package com.dxc.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySource(value = { "classpath:data-access.properties" })
@MapperScan("com.dxc.application.mybatis.mapper")
public class AppMyBatisDatabaseConfig {
	
	@Value("${jdbc.driverClassName}")
	private String jdbcDriverClassName;
	@Value("${jdbc.url}")
	private String jdbcURL;
	@Value("${jdbc.username}")
	private String jdbcUserName;
	@Value("${jdbc.password}")
	private String jdbcPassword;
	
	@Bean
	@Primary
	@Qualifier("myBatisDataSource")
    public DataSource getMyBatisDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(jdbcDriverClassName);
		ds.setUrl(jdbcURL);
		ds.setUsername(jdbcUserName);
		ds.setPassword(jdbcPassword);
		return ds;
    }
	@Bean
	@Qualifier("mybastistx")
	public DataSourceTransactionManager getMyBatisTransactionManager() {
		return new DataSourceTransactionManager(getMyBatisDataSource());
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(getMyBatisDataSource());
		SqlSessionFactory sessionFactory = sessionFactoryBean.getObject();
		sessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
		return sessionFactory;
	}
}
