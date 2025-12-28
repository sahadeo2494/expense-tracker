package com.example.expense_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .csrf(csrf  -> csrf.disable())
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/v1/auth/**",
                                        "/actuator/health",
                                        "/api/v1/transactions/publish",
                                        "/api/v1/categories/*",
                                        "/api/v1/accounts/*"
                                ).permitAll()
                                .anyRequest().authenticated()
                        )
                                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()) );


        return http.build();
    }
}
