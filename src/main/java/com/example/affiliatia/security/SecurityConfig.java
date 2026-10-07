package com.example.affiliatia.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // Auth
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Lecture publique
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/articles/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/categories/**"
                        ).permitAll()

                        // Création
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/articles/**"
                        ).hasAnyRole("ADMIN", "EDITOR")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/categories/**"
                        ).hasAnyRole("ADMIN", "EDITOR")

                        // Modification
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/articles/**"
                        ).hasAnyRole("ADMIN", "EDITOR")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/categories/**"
                        ).hasAnyRole("ADMIN", "EDITOR")

                        // Suppression → ADMIN uniquement
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/articles/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/categories/**"
                        ).hasRole("ADMIN")
                                // Tags - lecture publique
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/tags/**"
                                ).permitAll()

// Tags - création
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/v1/tags/**"
                                ).hasAnyRole("ADMIN", "EDITOR")

// Tags - modification
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/v1/tags/**"
                                ).hasAnyRole("ADMIN", "EDITOR")

// Tags - suppression
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/v1/tags/**"
                                ).hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/articles/*/schema"
                                ).permitAll()
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/articles/*/schema"
                                ).permitAll()
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/analytics/**"
                                ).permitAll()
                                .requestMatchers(
                                        "/sitemap.xml",
                                        "/robots.txt"
                                ).permitAll()
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/r/**"
                                ).permitAll()


                                // Tout le reste nécessite un login
                        .anyRequest().authenticated()

                )

                // Notre filtre JWT passe avant le filtre Spring standard
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }


}