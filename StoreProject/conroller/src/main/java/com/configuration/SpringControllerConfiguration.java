package com.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.ViewResolversBeanDefinitionParser;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.ViewResolverComposite;

@Configuration
@ComponentScan("com")
@EnableWebMvc
public class SpringControllerConfiguration implements WebMvcConfigurer {

//    private final ApplicationContext applicationContext;
//
//    public SpringControllerConfiguration(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }

//    @Bean
//    public InternalResourceViewResolver viewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
////        resolver.setApplicationContext(applicationContext);
//        resolver.setPrefix("/WEB-INF/view/");
//        resolver.setSuffix(".jsp");
//        return resolver;
//    }


}
