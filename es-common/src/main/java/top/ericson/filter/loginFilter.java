package top.ericson.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import top.ericson.pojo.User;
import top.ericson.util.JwtUtil;

/**
 * @author Ericson
 * @class loginFilter
 * @date 2020/04/11 18:33
 * @version 1.0
 * @description 登录过滤器
 */
@Slf4j
@Component
@WebFilter(filterName = "loginFilter", urlPatterns = {"/user/cheak/*", "/item/*", "/items/*", "/instock", "/instocks",
    "/supplier", "/suppliers", "/order", "/orders"})
public class loginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
        throws IOException, ServletException {
        System.out.println("loginFilter.doFilter()");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String jwt = request.getHeader("token");
        log.debug("jwt:{}", jwt);
        if (jwt == null || "".equals(jwt)) {
            log.debug("token为null");
            HttpServletResponse response = (HttpServletResponse)servletResponse;
            response.sendRedirect("/user/login");
            chain.doFilter(servletRequest, servletResponse);
        }
        Jws<Claims> jws = JwtUtil.build()
            .cheakJwt(jwt);
        if (jws != null) {
            User user = JwtUtil.build()
                .perseJwt(jws);
            log.debug("user:{}", user);
            request.setAttribute("userId", user.getUserId());
            request.setAttribute("username", user.getUsername());
            chain.doFilter(servletRequest, servletResponse);
        } else {
            log.debug("token验证失败");
            HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
            httpServletResponse.sendRedirect("/user/login");
            chain.doFilter(servletRequest, servletResponse);
        }
    }
}