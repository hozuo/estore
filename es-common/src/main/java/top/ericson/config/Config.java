package top.ericson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * @author Ericson
 * @class Config
 * @date 2020/04/04 19:56
 * @version 1.0
 * @description 
 */
@Configuration
public class Config {
    public static final String VERSION = "/v10";

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
