package kr.ac.hufs.ice.ice.config;

import kr.ac.hufs.ice.ice.filter.JwtAuthenticationFilter;
import kr.ac.hufs.ice.ice.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtUtil jwtUtil;
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter(jwtUtil));
        registrationBean.addUrlPatterns("/api/posts/*");
        registrationBean.setOrder(1); // 순서 지정, 필터가 여러 개면 중요
        return registrationBean;
    }

}
