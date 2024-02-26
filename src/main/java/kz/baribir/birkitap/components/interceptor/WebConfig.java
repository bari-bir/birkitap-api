package kz.baribir.birkitap.components.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JWTInterceptor jwtInterceptor;

    @Autowired
    private CrossInterceptor crossInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //WebMvcConfigurer.super.addInterceptors(registry);
        String[] excludePathPatterns = new String[]{
                "/public/**",
                "/auth/**"
        };
        registry.addInterceptor(crossInterceptor).addPathPatterns("/**");
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);
    }
}
