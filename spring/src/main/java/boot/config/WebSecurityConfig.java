package boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
/*
        http.authorizeHttpRequests((requests) -> requests
            .requestMatchers("/api/orders/**")
            .authenticated())
            .oauth2ResourceServer((server) -> server.jwt());
  */
        http.cors(Customizer.withDefaults());            

        return http.build();

    }


}
