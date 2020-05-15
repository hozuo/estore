package top.ericson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import top.ericson.service.FeignTokenHeaderRequestInterceptor;

/**
 * @author Ericson
 * @class FeginClientConfig
 * @date 2020/05/14 20:58
 * @version 1.0
 * @description feign客户端设置
 */
@Configuration
public class FeginClientConfig {

    /**
     * @author Ericson
     * @date 2020/05/14 20:58
     * @return
     * @description 注册请求拦截器，用于转发token
     */
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new FeignTokenHeaderRequestInterceptor();
    }
    
}