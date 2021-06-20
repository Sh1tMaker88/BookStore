package com.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;
import java.util.Objects;

//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//public class AclConfig extends GlobalMethodSecurityConfiguration {
//
//    private final DataSource dataSource;
//
//    @Autowired
//    public AclConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    @Bean
//    public PermissionGrantingStrategy permissionGrantingStrategy() {
//        return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
//    }
//
//    @Bean
//    public AclAuthorizationStrategy aclAuthorizationStrategy() {
//        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMIN"));
//    }
//
//    @Bean
//    public EhCacheManagerFactoryBean aclCacheManager() {
//        return new EhCacheManagerFactoryBean();
//    }
//
//    @Bean
//    public EhCacheFactoryBean aclEhcacheFactoryBean() {
//        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
//        ehCacheFactoryBean.setCacheManager(Objects.requireNonNull(aclCacheManager().getObject()));
//        ehCacheFactoryBean.setCacheName("aclCache");
//        return ehCacheFactoryBean;
//    }
//
//    @Bean
//    public EhCacheBasedAclCache aclCache() {
//        return new EhCacheBasedAclCache(aclEhcacheFactoryBean().getObject(),
//                permissionGrantingStrategy(), aclAuthorizationStrategy());
//    }
//
//    @Bean
//    public LookupStrategy lookupStrategy() {
//        return new BasicLookupStrategy(dataSource, aclCache(),
//                aclAuthorizationStrategy(), new ConsoleAuditLogger());
//    }
//
//    @Bean
//    public JdbcMutableAclService aclService() {
//        return new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
//    }
//
//    @Override
//    protected MethodSecurityExpressionHandler createExpressionHandler() {
//        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
//        AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(aclService());
//        expressionHandler.setPermissionEvaluator(permissionEvaluator);
//        expressionHandler.setPermissionCacheOptimizer(new AclPermissionCacheOptimizer(aclService()));
//        return expressionHandler;
//    }
//}
