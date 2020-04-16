package top.ericson;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableZuulProxy
@EnableDiscoveryClient
@EnableSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EsZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsZuulApplication.class, args);
    }

}
