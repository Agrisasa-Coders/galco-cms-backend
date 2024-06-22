package com.gapco.backend.config;

import com.gapco.backend.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(authorizeHttpRequestCustomizer-> authorizeHttpRequestCustomizer
                        .requestMatchers(
                                mvc.pattern(AppConstants.BASE_URI+"/**"),
                                mvc.pattern(AppConstants.BASE_URI+"/auth/**"),
                                mvc.pattern(AppConstants.BASE_URI+"/institution/**"),
                                mvc.pattern(AppConstants.BASE_URI+"/role/**"),
                                mvc.pattern(AppConstants.BASE_URI+"/permission/**"),
                                mvc.pattern(AppConstants.BASE_URI+"/configuration"),
                                mvc.pattern(AppConstants.BASE_URI+"/tests/**"),
                                mvc.pattern("zff-api-doc"),
                                mvc.pattern("/zff-api-doc/**"),
                                mvc.pattern("zff-api"),
                                mvc.pattern("/swagger-resources/**"),
                                mvc.pattern("/swagger-ui.html**"),
                                mvc.pattern("/swagger-ui/**"),
                                mvc.pattern("/webjars/**"),
                                mvc.pattern("favicon.ico")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionConfigure ->
                                sessionConfigure.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Scope("prototype")
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

}
