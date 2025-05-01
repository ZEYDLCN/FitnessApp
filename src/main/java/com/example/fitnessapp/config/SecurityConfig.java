package com.example.fitnessapp.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// HttpMethod importu belki de gerekmiyordu, emin olmak için kontrol edin
// import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    // 1. H2 Konsolu için İzin (Özel RequestMatcher)
                    .requestMatchers(PathRequest.toH2Console()).permitAll()
                    // 2. Diğer herkese açık yollar (String path'ler)
                    .requestMatchers(
                        "/api/users/**",
                        "/api/exercises/**",
                        "/api/workouts/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                     ).permitAll()
                    // Geriye kalan tüm istekler kimlik doğrulaması gerektirir
                    .anyRequest().authenticated()
            )
            .httpBasic(withDefaults())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers(PathRequest.toH2Console()) // H2 için ignore
                    .ignoringRequestMatchers("/api/**")                 // API için ignore
            );

        return http.build();
    }
}