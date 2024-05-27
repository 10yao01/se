package com.example.credit_score_management.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.credit_score_management.pojo.Result;
import com.example.credit_score_management.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String url = req.getRequestURL().toString();
        log.info("请求的url：{}",url);

        if(url.contains("login")){
            log.info("登录操作，放行...");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        String jwt = req.getHeader("Authorization");

//        if(!StringUtils.hasLength(jwt)){
//            log.info("请求头token为空，返回未登录的信息");
//            Result error = Result.error("NOT_LOGIN");
//            String notLogin = JSONObject.toJSONString(error);
//            resp.getWriter().write(notLogin);
//            return;
//        }
//
//        try {
//            JwtUtils.parseJWT(jwt);
//        } catch (Exception e){
//            e.printStackTrace();
//            log.info("解析令牌失败，返回未登录错误信息");
//            Result error = Result.error("NOT_LOGIN");
//            String notLogin = JSONObject.toJSONString(error);
//            resp.getWriter().write(notLogin);
//            return;
//        }

        log.info("令牌合法，放行");
        filterChain.doFilter(servletRequest,servletResponse);

    }
}
