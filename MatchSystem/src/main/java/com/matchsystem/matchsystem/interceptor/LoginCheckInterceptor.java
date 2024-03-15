package com.matchsystem.matchsystem.interceptor;
import com.alibaba.fastjson.util.*;
import com.matchsystem.matchsystem.pojo.Result;
import com.matchsystem.matchsystem.utils.JwtUtils;
import com.nimbusds.jose.shaded.json.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
@Slf4j

@Component
public class LoginCheckInterceptor  implements HandlerInterceptor {
    @Override//目标资源运行前运行，返回ture放行，返回flase不放行
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        String url=req.getRequestURL().toString();
        log.info("请求的url:{}",url);
        if(url.contains("login")||url.contains("/register")){
            log.info("登录操作放行");
            return true;
        }
        /*String jwt = req.getHeader("token");
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头token为空,返回未登录信息");
            Result error=Result.error("NOT_LOGIN");
            String notLogin= com.alibaba.fastjson.JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return false;*/
        String authorizationHeader = req.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // 如果 Authorization 头部字段不存在或者格式不正确，返回未登录信息
            log.info("请求头中未包含有效的Bearer Token，返回未登录信息");
            Result error=Result.error("NOT_LOGIN");
            String notLogin= com.alibaba.fastjson.JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return false;
        }

        // 提取出 token 部分
        String jwt = authorizationHeader.substring(7); // 去除 "Bearer " 前缀


        try{
            JwtUtils.verify(jwt);

        }catch (Exception e){
            e.printStackTrace();
            log.info("解析令牌失败，返回错误登录信息");
            Result error=Result.error("NOT_LOGIN");
            String notLogin= com.alibaba.fastjson.JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
        }
        log.info("令牌合法，通过");
        return true;
    }

    @Override//目标资源运行后运行，返回ture放行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle running");
    }

    @Override//视图渲染完后运行,最后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterHandle running");
    }
}
