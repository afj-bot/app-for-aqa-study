package com.afj.solution.buyitapp.config.prod;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Tomash Gombosh
 */
@EnableSwagger2
@Configuration
@Profile({"prod"})
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedMethods("*")
                .allowedOriginPatterns("https://buy-it.afj-solution.com:[*]")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
