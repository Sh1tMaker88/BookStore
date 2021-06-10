package com.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
//@ComponentScan("com")
@PropertySource("classpath:server.properties")
@EnableTransactionManagement/*(proxyTargetClass = true)*/
public class SpringPersistenceConfig {

//    private final ApplicationContext applicationContext;
    @Value("${URL}")
    private String url;
    @Value("${NAME}")
    private String username;
    @Value("${PASSWORD}")
    private String password;
    @Value("${DRIVER}")
    private String driver;
    @Value("${PACKAGES_TO_SCAN}")
    private String packageToScan;
    @Value("${DIALECT}")
    private String dialect;

//    @Autowired
//    public SpringPersistenceConfig(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(driver);
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        return dataSource;
//    }

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(driver);
            dataSource.setJdbcUrl(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);
        } catch (PropertyVetoException e) {
            e.getLocalizedMessage();
        }
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(DIALECT, dialect);
        properties.put(SHOW_SQL, true);
        properties.put(FORMAT_SQL, true);
        properties.put(USE_SQL_COMMENTS, true);
        properties.put(USE_GET_GENERATED_KEYS, true);
//        properties.put(HBM2DDL_AUTO, "update");
        return properties;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean =  new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(packageToScan);
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        transactionManager.setNestedTransactionAllowed(true);
        return transactionManager;
    }

}
