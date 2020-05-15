package top.ericson.service;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author Ericson
 * @class FeignTokenHeaderRequestInterceptor
 * @date 2020/05/14 20:58
 * @version 1.0
 * @description 用于在feign请求发送时转发token，需要自定义隔离策略
 */
@Slf4j
@Component
public class FeignTokenHeaderRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();

            if (request != null) {
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String headerName = headerNames.nextElement();
                        log.debug("headerName:{}", headerName);
                        if (headerName.equals("token")) {
                            
                            String headerValue = request.getHeader(headerName);
                            log.debug("headerValue:{}", headerValue);
                            requestTemplate.header(headerName, headerValue);
                        }
                    }
                }
            }
        }
    }
}
