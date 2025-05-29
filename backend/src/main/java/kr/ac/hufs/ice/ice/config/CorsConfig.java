package kr.ac.hufs.ice.ice.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry r) {
        r.addMapping("/api/**")                       // 백엔드 REST 경로
         .allowedOrigins("http://localhost:3000")     // 프론트 origin
         .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
         .allowedHeaders("*")
         .allowCredentials(true)
         .maxAge(3600);
    }
}

