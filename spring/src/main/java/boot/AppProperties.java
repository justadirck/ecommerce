package boot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Validated
@ConfigurationProperties(prefix = "app.prop")
@EnableConfigurationProperties(AppProperties.class)
public class AppProperties {
    
    @NotEmpty
    private String allowedOrigins;

}
