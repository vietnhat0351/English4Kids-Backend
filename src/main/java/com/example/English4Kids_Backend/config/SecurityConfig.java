package com.example.English4Kids_Backend.config;

import com.example.English4Kids_Backend.dtos.AuthResponse;
import com.example.English4Kids_Backend.entities.User;
import com.example.English4Kids_Backend.repositories.UserRepository;
import com.example.English4Kids_Backend.services.Oauth2Service;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;

import java.util.List;
import java.util.Optional;

import static com.example.English4Kids_Backend.entities.Permission.*;
import static com.example.English4Kids_Backend.entities.Role.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;
    private final Oauth2Service oauth2Service;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> {
                    httpSecurityCorsConfigurer.configurationSource(request -> {
                        var cors = new CorsConfiguration();
                        cors.setAllowedOrigins(List.of("*"));
                        cors.setAllowedMethods(List.of("*"));
                        cors.setAllowedHeaders(List.of("*"));
                        return cors;
                    });
                })
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/oauth2/authorization/**",
                                "/api/v1/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/ws/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
                    httpSecurityOAuth2LoginConfigurer
                            .successHandler((request, response, authentication) -> {
                                OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
                                OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

                                AuthResponse authResponse = oauth2Service.authenticate(oAuth2User);

                                String script = "<script>" +
                                        "window.opener.postMessage({authResponse: " + Json.pretty(authResponse) + "}, 'http://localhost:3000');" +
                                        "window.close();" +
                                        "</script>";

                                // Write the script to the response
                                response.setContentType("text/html");
                                response.getWriter().write(script);

                            })
                            .failureHandler((request, response, exception) -> {
                                response.sendRedirect("http://localhost:3000");
                            });
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
