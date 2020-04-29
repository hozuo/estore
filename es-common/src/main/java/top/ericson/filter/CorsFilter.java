package top.ericson.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import top.ericson.util.JwtUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ericson
 * @class CorsFilter
 * @date 2020/04/28 19:12
 * @version 1.0
 * @description 跨域配置
 */
@Slf4j
@Component
@WebFilter(filterName = "corsFilter", urlPatterns = {"/*"})
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        System.out.println("CorsFilter.doFilter()");
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        HttpServletRequest httpRequest = (HttpServletRequest)request;

        // OPTIONS
        if ("OPTIONS".equals(httpRequest.getMethod())) {
            httpResponse.setHeader("Access-control-Allow-Origin", httpRequest.getHeader("Origin"));
            httpResponse.setHeader("Access-Control-Allow-Methods","POST, GET, OPTIONS, PUT, DELETE");
            httpResponse.setHeader("Access-Control-Allow-Headers",
                httpRequest.getHeader("Access-Control-Request-Headers"));
            log.debug("httpRequest.getHeader(\"Access-Control-Request-Headers\"):{}", httpRequest.getHeader("Access-Control-Request-Headers"));
            httpResponse.setStatus(200);
            System.out.println("OPTIONS,return");
            return;
        }

        // 其他请求的跨域处理
        log.debug(httpRequest.getHeader("Access-Control-Request-Headers"));
        log.debug(httpRequest.getMethod());
        httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
        httpResponse.setHeader("Access-Control-Allow-Methods", httpRequest.getMethod());
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
