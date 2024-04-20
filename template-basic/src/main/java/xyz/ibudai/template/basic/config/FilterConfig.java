package xyz.ibudai.template.basic.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.ibudai.template.basic.filter.TokenFilter;
import xyz.ibudai.template.basic.util.URIPattenUtil;

import java.util.Collections;

@Configuration
public class FilterConfig {

    @Value("${basic.filter.excludes}")
    private String excludesApi;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean
    public FilterRegistrationBean<TokenFilter> orderFilter1() {
        FilterRegistrationBean<TokenFilter> filter = new FilterRegistrationBean<>();
        filter.setName("token-filter");
        // Set effect url
        filter.setUrlPatterns(Collections.singleton("/**"));
        // Set ignore url, when multiply the value spilt with ","
        String[] urls = URIPattenUtil.slice(excludesApi, contextPath);
        filter.addInitParameter("excludedUris", StringUtils.join(urls, ","));
        filter.setOrder(-1);
        filter.setFilter(new TokenFilter());
        return filter;
    }
}
