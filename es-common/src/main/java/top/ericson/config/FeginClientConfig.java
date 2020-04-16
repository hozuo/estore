package top.ericson.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeginClientConfig {

    @Autowired
    private HttpServletRequest request;

    @Bean
    public RequestInterceptor headerInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                requestTemplate.header("token", request.getHeader("token"));
            }
        };
    }

}