package io.getarrays.contactapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()); /* AbstractHttpConfigurer::disable */
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/admin/**").hasRole("ADMIN") // /admin/** 접근은 ADMIN 권한을 가진 사용자만 가능
                        .requestMatchers("/api/users/manager/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/api/users/user/**").authenticated() // /user/** 접근은 인증된 사용자만 가능
                        .anyRequest().permitAll() // 그 외 모든 요청은 허용
                );
        http.formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc") // 실제 로그인 처리 엔드 포인트
                        .usernameParameter("email") // form에서 userEmail 사용
                        .defaultSuccessUrl("/api/users/loginOk")
                );
        http.logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/api/users/logoutOk")
                        .deleteCookies("JSESSIONID")
                );

        http.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000"); // 리액트 서버
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
