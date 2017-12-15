package com.vivy.config;

import java.beans.PropertyVetoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class CustomConfig extends WebMvcConfigurerAdapter{
 
    @Value("${c3p0.url}")
    private String url;
 
    @Value("${c3p0.username}")
    private String username;
 
    @Value("${c3p0.password}")
    private String password;
 
    @Value("${c3p0.driverClassName}")
    private String driverClassName;
    
    @Autowired
    ComboPooledDataSource dataSource;
    
 
    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setJdbcUrl(url);
		dataSource.setPassword(password);
		dataSource.setUser(username);
		dataSource.setDriverClass(driverClassName);
		dataSource.setMinPoolSize(5);
		dataSource.setAcquireIncrement(5);
		dataSource.setMaxPoolSize(20);
		dataSource.setMaxStatements(100);
		dataSource.setPreferredTestQuery("SELECT 1");
		dataSource.setPropertyCycle(3);
		dataSource.setUnreturnedConnectionTimeout(60);
		dataSource.setTestConnectionOnCheckout(false);
		dataSource.setTestConnectionOnCheckin(true);
		dataSource.setDebugUnreturnedConnectionStackTraces(true);
		dataSource.setMaxIdleTimeExcessConnections(300);
		dataSource.setIdleConnectionTestPeriod(60);  
		
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate JdbcTemplate() {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate();
    	jdbcTemplate.setDataSource(dataSource);
    	jdbcTemplate.setLazyInit(false);
    	return jdbcTemplate;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager()
    {
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
}
