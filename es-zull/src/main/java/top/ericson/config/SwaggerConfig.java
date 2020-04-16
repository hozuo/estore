package top.ericson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(new ApiInfoBuilder().title("Ericson_jt信息管理系统_接口文档")
            .description("描述：estore-zull接口文档")
            .contact(new Contact("郭虹佐", "http://www.ericson.top", "ghzfree@163.com"))
            .termsOfServiceUrl("http://www.ericson.top")
            .version("1.0")
            .build())
            .select()
            .apis(RequestHandlerSelectors.basePackage("top.ericson.controller"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Spring Cloud Demo Api")
            .contact(new Contact("dukunbiao(null)", "https://blog.csdn.net/dkbnull", ""))
            .version("1.0.0")
            .description("Spring Cloud Demo")
            .build();
    }
}