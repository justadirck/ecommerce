package boot.config;

import boot.AppProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyAppConfig implements WebMvcConfigurer {

    @Value("${spring.data.rest.base-path}")
    private String basePath;

    private AppProperties properties;

    MyAppConfig(AppProperties properties){
        this.properties = properties;
    }

    @Override
    public void addCorsMappings(CorsRegistry cors) {

        cors.addMapping(this.basePath + "/**").allowedOrigins(this.properties.getCors());
        
    }
}










