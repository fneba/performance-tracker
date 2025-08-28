package com.sports.performance_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // allow testing in Insomnia
                .csrf(csrf -> csrf.disable())

                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/health").permitAll()  // opens health endpoint
                        .anyRequest().authenticated()                        // all else requires authentication
                )

                // Temporary; enables HTTP Basic so protected routes can be tested easily
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
