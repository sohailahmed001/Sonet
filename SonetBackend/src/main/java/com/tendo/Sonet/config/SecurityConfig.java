package com.tendo.Sonet.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import com.tendo.Sonet.filter.*;
import jakarta.servlet.http.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.www.*;
import org.springframework.security.web.csrf.*;
import org.springframework.web.cors.*;
import java.io.IOException;
import java.util.*;


@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    private static final String[] AUTHENTICATED_APIS    =   { "/api/test", "/api/users/**", "/api/roles/**", "/api/authorities/**", "/api/sonet/**"};
    private static final String[] GET_PERMITTED_APIS    =   { "/api/login" };
    private static final String[] POST_PERMITTED_APIS   =   { "/api/register" };
    private static final String[] CSRF_IGNORE_APIS      =   { "/api/register" };

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception
    {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationHelper()))
                .csrf((csrf) -> csrf
                        .sessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy())
                        .csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers(CSRF_IGNORE_APIS)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .addFilterAfter(getCsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(getJWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(getJWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(AUTHENTICATED_APIS).authenticated()
                    .requestMatchers(HttpMethod.GET, GET_PERMITTED_APIS).permitAll()
                    .requestMatchers(HttpMethod.POST, POST_PERMITTED_APIS).permitAll())
                .httpBasic(httpBasicCustomizer -> httpBasicCustomizer.authenticationEntryPoint(getCustomBasicAuthenticationEntryPoint()));

        return http.build();
    }

    public BasicAuthenticationEntryPoint getCustomBasicAuthenticationEntryPoint()
    {
        return new CustomBasicAuthenticationEntryPoint();
    }

    // Custom entry point to handle authentication failure
    private static class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint
    {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        @Override
        public void afterPropertiesSet()
        {
            setRealmName("Realm");
            super.afterPropertiesSet();
        }
    }

    public CsrfCookieFilter getCsrfCookieFilter()
    {
        return new CsrfCookieFilter();
    }

    public JWTTokenValidatorFilter getJWTTokenValidatorFilter()
    {
        return new JWTTokenValidatorFilter();
    }

    public JWTTokenGeneratorFilter getJWTTokenGeneratorFilter()
    {
        return new JWTTokenGeneratorFilter();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Module hibernateJakartaModule()
    {
        return new Hibernate5JakartaModule();
    }
}

class CorsConfigurationHelper implements CorsConfigurationSource
{
    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request)
    {
        CorsConfiguration   corsConfig  =   new CorsConfiguration();

        // set this maybe to specific when we deploy
        corsConfig.setAllowedOriginPatterns(Collections.singletonList("*"));
        corsConfig.setAllowedMethods(Collections.singletonList("*"));
        corsConfig.setAllowedHeaders(Collections.singletonList("*"));
        corsConfig.setExposedHeaders(Collections.singletonList("Authorization")); // FOR JWT
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);

        return corsConfig;
    }
}
