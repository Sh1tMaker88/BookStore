package com.controller.webConfiguration;

import com.security.configuration.MySecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan({"com.controller"})
@EnableWebMvc
@Import(MySecurityConfig.class)
public class SpringControllerConfiguration implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    @Autowired
    public SpringControllerConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
