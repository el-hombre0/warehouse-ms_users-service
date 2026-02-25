package ru.evendot.userservice.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/v1/users/**").permitAll()
//                .requestMatchers(SIGNUP_ENTRY_POINT).permitAll()
//                .requestMatchers(SWAGGER_ENTRY_POINT).permitAll()
//                .requestMatchers(API_DOCS_ENTRY_POINT).permitAll()
//                .requestMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll()
                .anyRequest().authenticated()
        ).httpBasic(withDefaults());
        return http.build();
    }
}
