package com.afj.solution.productApp.config;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Tomash Gombosh
 */
@EnableSwagger2
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(
                        Sort.class,
                        JsonArray.class,
                        JsonObject.class,
                        JsonNull.class)
                .apiInfo(data())
                .securitySchemes(Stream.of(apiKey()).collect(Collectors.toList()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.afj.solution.productApp.controller.api.v1"))
                .build()
                .securitySchemes(Stream.of(apiKey()).collect(Collectors.toList()));
    }

    private ApiInfo data() {
        final String projectVersion = System.getenv("VERSION");
        return new ApiInfoBuilder()
                .title("E-commerce api")
                .description("E-commerce API for study automation")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .version(projectVersion)
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }

}
