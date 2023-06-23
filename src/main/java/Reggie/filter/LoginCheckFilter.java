package Reggie.filter;

import Reggie.common.BaseContext;
import Reggie.common.R;
import com.alibaba.fastjson.JSON;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginCheckFilter" , urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配
    public static final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取访问路径
        String requestURI = request.getRequestURI();

        //设置无需拦截的页面
        String path [] = new String[]{
          "/employee/login",
          "/employee/logout",
          "/backend/**",
          "/front/**"
        };

        boolean filterStatus = check(path , requestURI);

        //1、状态1：直接放行
        if (filterStatus){
            filterChain.doFilter(request , response);
            return;
        }

        //2、状态2：用户已登录时放行(上述未通过)
        if (request.getSession().getAttribute("employee") != null){
            Long empId = (Long) request.getSession().getAttribute("employee");

            BaseContext .setCurrentId(empId);
            filterChain.doFilter(request , response);
            return;
        }

        //未登录处理
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    public boolean check(String [] url , String requestURI){
        for (String s : url) {
            boolean match = pathMatcher.match(s , requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
