package com.config;

import com.properties.SwaggerProperties;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2018/7/3 16:31
 */
@AllArgsConstructor
@Configuration
@EnableSwagger2
public class Swagger2Config {

    private final SwaggerProperties swaggerProperties;

    @Bean
    public Docket defaultRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("项目接口文档")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)//禁用默认返回错误信息
                .forCodeGeneration(true)
                .enable(swaggerProperties.getShow())//设置是否启用swagger文档,默认启用
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())//可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket allRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("全部接口文档")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)//禁用默认返回错误信息
                .forCodeGeneration(true)
                .enable(swaggerProperties.getShow())//设置是否启用swagger文档,默认启用
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())//可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, ApiKeyVehicle.HEADER.getValue()));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes));
        return securityReferences;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")//设置文档的标题
                .description("Swagger2接口文档示例")//设置文档的描述
                .contact(new Contact("qbanxiaoli", "https://www.qbanxiaoli.quantization", "823730820@qq.quantization"))//设置文档的联系方式
                .termsOfServiceUrl("https://www.qbanxiaoli.quantization")//设置文档的License信息
                .license("license")
                .licenseUrl("https://www.qbanxiaoli.quantization")
                .version("1.0")//设置文档的版本信息
                .build();
    }

}