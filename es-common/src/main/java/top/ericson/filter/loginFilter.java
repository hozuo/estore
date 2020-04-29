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
@WebFilter(filterName = "loginFilter", urlPatterns = {"/*"})
public class loginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
        throws IOException, ServletException {
        log.debug("loginFilter.doFilter()");
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpServletRequest request = (HttpServletRequest)servletRequest;

        String path = request.getRequestURI()
            .substring(request.getContextPath()
                .length())
            .replaceAll("[/]+$", "");
        log.debug("path:{}", path);
        String allowedPath = "/user/login";

        // 判断是否需要登录
        if (allowedPath.equals(path)) {
            log.debug("不校验token");
            chain.doFilter(request, response);
            return;
        } else {
            String jwt = request.getHeader("token");
            log.debug("jwt:{}", jwt);
            if (jwt == null || "".equals(jwt)) {
                log.debug("token为null");
                response.setStatus(600);
                response.setCharacterEncoding("utf-8");
                response.getWriter()
                    .write("{\"status\": \"600\",\"msg\": \"用户未登录\",\"data\": null}");
                return;
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
                response.setStatus(600);
                response.setCharacterEncoding("utf-8");
                response.getWriter()
                    .write("{\"status\": \"600\",\"msg\": \"登录信息验证失败\",\"data\": null}");
                return;
            }
        }

    }
}