package xyz.ibudai.template.basic.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.ibudai.template.basic.util.JwtTokenUtil;
import xyz.ibudai.template.basic.model.ResponseData;
import xyz.ibudai.template.basic.util.URIPattenUtil;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class TokenFilter implements Filter {

    @Value("${basic.filter.enable}")
    private Boolean enableFilter;

    @Value("${basic.filter.excludes}")
    private String excludesPattern;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    /**
     * 每次请求读取请求头 Token 验证是否登录
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!enableFilter) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // Login validate
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String target = req.getRequestURI();
        if (URIPattenUtil.march(target, excludesPattern, contextPath)) {
            // White list
            filterChain.doFilter(req, servletResponse);
            return;
        }

        int status;
        String msg;
        String token = req.getHeader("Token");
        if (StringUtils.isNoneBlank(token)) {
            if (!JwtTokenUtil.expired(token)) {
                // Token verify pass
                filterChain.doFilter(req, servletResponse);
                return;
            } else {
                status = 203;
                msg = "Login expired.";
            }
        } else {
            status = 203;
            msg = "Please login and try again.";
        }
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        ResponseData result = new ResponseData(status, msg, null);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
