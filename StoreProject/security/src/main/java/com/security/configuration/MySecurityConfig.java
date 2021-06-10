package com.security.configuration;

import com.security.userService.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//@Import(AclConfig.class)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

//    private MyAuthenticationProvider authenticationProvider;
//    private MyUserDetailsService userDetailsService;
    private final DataSource dataSource;

    @Autowired
    public MySecurityConfig(
//            MyUserDetailsService userDetailsService,
            DataSource dataSource) {
        this.dataSource = dataSource;
//        this.userDetailsService = userDetailsService;
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
////            .authenticationProvider(authenticationProvider)
//            .userDetailsService(userDetailsService)
//            .passwordEncoder(passwordEncoder());
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .authenticationProvider(authenticationProvider);
//        auth
//                .userDetailsService(userDetailsService);
        auth
            .jdbcAuthentication()
            .dataSource(dataSource)
//            .usersByUsernameQuery("SELECT username, password, enabled FROM users AS us WHERE us.username=?")
//            .authoritiesByUsernameQuery("SELECT username,authority FROM authorities WHERE username=?")
//            .groupAuthoritiesByUsername("SELECT username, role_name FROM users JOIN users_role " +
//        "USING (username) WHERE username=?")
            .passwordEncoder(passwordEncoder());
//            .rolePrefix("ROLE_");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/registration").not().fullyAuthenticated()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/").permitAll()
//                .anyRequest().authenticated()
            .and()
                .formLogin()
                .defaultSuccessUrl("/")
                .permitAll()
            .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
            .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
