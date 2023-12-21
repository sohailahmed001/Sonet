package com.tendo.Sonet.filter;

import com.tendo.Sonet.utils.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter
{

    private static  final   String    ISSUER     =   SecurityConstants.JWT_ISSUER;
    private static  final   String    SUBJECT    =   "JWT Token";
    public  static  final   String    LOGIN_API  =   "/api/login";
    public  static  final   String    REGISTER_API  =   "/api/register";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null)
        {
            SecretKey   key =   Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String      jwt =   Jwts.builder()
                                    .setIssuer(ISSUER)
                                    .setSubject(SUBJECT)
                                    .claim("username", authentication.getName())
                                    .claim("authorities", convertAuthorities(authentication.getAuthorities()))
                                    .setIssuedAt(new Date())
                                    .setExpiration(new Date(new Date().getTime() + 20000000))
                                    .signWith(key).compact();

            response.setHeader(SecurityConstants.JWT_HEADER, jwt);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException
    {
        return !request.getServletPath().equals(LOGIN_API); // Generate Token only on LOGIN
    }

    private String convertAuthorities(Collection<? extends GrantedAuthority> authorities)
    {
        Set<String> authoritiesSet = new HashSet<>();

        for (GrantedAuthority authority : authorities)
        {
            authoritiesSet.add(authority.getAuthority());
        }

        return String.join(",", authoritiesSet);
    }
}
