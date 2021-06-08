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

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//@EnableTransactionManagement(proxyTargetClass = true)
//@Import(AclConfig.class)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

//    private final MyAuthenticationProvider authenticationProvider;
    private MyUserDetailsService userDetailsService;
    private final DataSource dataSource;

    @Autowired
    public MySecurityConfig(
//            MyAuthenticationProvider authenticationProvider,
            DataSource dataSource) {
//        this.authenticationProvider = authenticationProvider;
        this.dataSource = dataSource;
    }

    @Autowired
    public void setUserDetailsService(final MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider);
        auth.jdbcAuthentication()
            .dataSource(dataSource)
//            .usersByUsernameQuery("SELECT username, password, id FROM user WHERE username=?")
//            .authoritiesByUsernameQuery("SELECT user_id, roles FROM user_role WHERE user_id=" +
//                    "(SELECT id FROM user WHERE username=?)")
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .defaultSuccessUrl("/")
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
