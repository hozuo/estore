package top.ericson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import top.ericson.service.FeignTokenHeaderRequestInterceptor;

@Configuration
public class FeginClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new FeignTokenHeaderRequestInterceptor();
    }
    
}