package com.example.authservice.security.config;


import com.example.authservice.entity.Role;
import com.example.authservice.mapper.MemberMapper;
import com.example.authservice.security.filter.CustomAuthenticationFilter;
import com.example.authservice.security.handler.CustomAuthenticationFailureHandler;
import com.example.authservice.security.handler.CustomAuthenticationSuccessHandlerForAdmin;
import com.example.authservice.security.handler.CustomAuthenticationSuccessHandlerForUser;
import com.example.authservice.security.provider.CustomAuthenticationProviderForAdmin;
import com.example.authservice.security.provider.CustomAuthenticationProviderForUser;
import com.example.authservice.security.service.CustomAdminDetailService;
import com.example.authservice.security.service.CustomPasswordEncoder;
import com.example.authservice.security.service.CustomUserDetailService;
import com.example.authservice.security.service.TokenCreateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final ObjectMapper objectMapper;
    private final MemberMapper memberMapper;
    private final TokenCreateService tokenCreateService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterAfter(customUserAuthenticationFilter(), LogoutFilter.class)
                .addFilterAfter(customAdminAuthenticationFilter(), LogoutFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    };

    @Bean
    public CustomPasswordEncoder customPasswordEncoder() {
        return new CustomPasswordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    @Primary
    public AuthenticationManager authenticationManagerForUser(CustomAuthenticationProviderForUser customAuthenticationProviderForUser) {
        return new ProviderManager(customAuthenticationProviderForUser);
    }

    @Bean
    public CustomAuthenticationProviderForUser customUserAuthenticationProvider() {
        return new CustomAuthenticationProviderForUser(new CustomUserDetailService(memberMapper), customPasswordEncoder());
    }

    @Bean
    public CustomAuthenticationFilter customUserAuthenticationFilter() {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerForUser(customUserAuthenticationProvider()), objectMapper);
        customAuthenticationFilter.setFilterProcessesUrl("/user/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandlerForUser(memberMapper, tokenCreateService, objectMapper));
        customAuthenticationFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler(objectMapper));
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Bean
    public CustomAuthenticationProviderForAdmin customAdminAuthenticationProvider() {
        return new CustomAuthenticationProviderForAdmin(new CustomAdminDetailService(memberMapper), customPasswordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManagerForAdmin(CustomAuthenticationProviderForAdmin customAuthenticationProviderForAdmin) {
        return new ProviderManager(customAuthenticationProviderForAdmin);
    }

    @Bean
    public CustomAuthenticationFilter customAdminAuthenticationFilter() {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerForAdmin(customAdminAuthenticationProvider()), objectMapper);
        customAuthenticationFilter.setFilterProcessesUrl("/admin/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandlerForAdmin(memberMapper, tokenCreateService, objectMapper));
        customAuthenticationFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler(objectMapper));
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
