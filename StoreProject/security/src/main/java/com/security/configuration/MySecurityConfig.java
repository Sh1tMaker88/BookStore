package com.security.configuration;

import com.security.exceptionHandling.MyAccessDeniedHandler;
import com.security.exceptionHandling.MyAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
//    private final MyAuthenticationEntryPoint entryPoint;
//    private final MyAccessDeniedHandler accessDenied;

    @Autowired
    public MySecurityConfig(
            DataSource dataSource
//            MyAuthenticationEntryPoint entryPoint,
//            MyAccessDeniedHandler accessDenied
    ) {
        this.dataSource = dataSource;
//        this.entryPoint = entryPoint;
//        this.accessDenied = accessDenied;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, enabled from users where username = ?")
            .authoritiesByUsernameQuery("SELECT username,role_name FROM users_role WHERE username=?")
            .groupAuthoritiesByUsername("SELECT id, username, role_name FROM users JOIN users_role " +
        "USING (username) WHERE username=?")
            .rolePrefix("ROLE_")
            .passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .defaultSuccessUrl("/")
                .permitAll()
            .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
//            .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(entryPoint)
//                .accessDeniedHandler(accessDenied)
            .and()
                .csrf()
                .disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
