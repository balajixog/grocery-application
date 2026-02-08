package com.bab.grocery_backend.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.bab.grocery_backend.util.JwtFilter;

@Configuration
public class SecurityConfig {
        

        @Bean
        public PasswordEncoder passwordEncoder() throws Exception{    //use encode and matches the validation 
                return new BCryptPasswordEncoder();
        }
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http,JwtFilter jwtFilter) throws Exception{
            http
                 .csrf(AbstractHttpConfigurer::disable)
                 .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                 .authorizeHttpRequests((auth)-> auth
                 .requestMatchers("/auth/**").permitAll()
                 .requestMatchers("/admin/**").hasRole("ADMIN")
                 .requestMatchers("/user/**")
                 .hasAnyRole("USER", "ADMIN")
                 .anyRequest().authenticated()
              )
              
                 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                 
                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource(){
                CorsConfiguration configuration=new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));   //replace with website url in the furture
                configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
                configuration.setAllowedHeaders(List.of("*"));

                UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);

                return source;
        }
    
}
