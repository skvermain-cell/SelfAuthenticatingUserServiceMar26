package com.scaler.selfauthenticatinguserservicemar26.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApplicationConfig {

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.cors(cors -> cors.disable());

        httpSecurity.authorizeHttpRequests(
                authorize -> authorize
                        .anyRequest().permitAll()
        );

        return httpSecurity.build();

        /*
        httpSecurity.authorizeHttpRequests(
                authorize -> authorize
                            .requestMatchers("/users/signup").permitAll()
                            .requestMatchers("/users/login").permitAll()
                            .anyRequest().authenticated()
        );

         */


    }
}

