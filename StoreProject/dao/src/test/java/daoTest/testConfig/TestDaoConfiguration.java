package daoTest.testConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:server.properties")
@ComponentScan({"com.dao", "com.model"})
public class TestDaoConfiguration {

    private final String driver = "org.h2.Driver";
    private final String url =
            "jdbc:h2:file:C:/Users/User/IdeaProjects/StoreProject/dao/MyTestDB;" +
                    "DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE";
    private final String username = "user";
    private final String password = "";
    @Value("${PACKAGES_TO_SCAN}")
    private String packageToScan;
    @Value("${PACKAGE_TO_SCAN_DAO_TEST}")
    private String packageToScan2;
    @Value("${TEST_DIALECT}")
    private String dialect;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
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
        sessionFactoryBean.setPackagesToScan(packageToScan, packageToScan2);
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
