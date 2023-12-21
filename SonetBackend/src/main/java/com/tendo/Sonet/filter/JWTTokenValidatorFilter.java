package com.tendo.Sonet.filter;

import com.tendo.Sonet.utils.SecurityConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


public class JWTTokenValidatorFilter extends OncePerRequestFilter
{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String  jwt =   request.getHeader(SecurityConstants.JWT_HEADER);

        if (jwt != null)
        {
            try
            {
                SecretKey       key             =   Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
                Claims          claims          =   Jwts.parserBuilder()
                                                        .setSigningKey(key)
                                                        .build()
                                                        .parseClaimsJws(jwt)
                                                        .getBody();

                String          username    =   String.valueOf(claims.get("username"));
                String          authorities =   (String) claims.get("authorities");
                Authentication  auth        =   new UsernamePasswordAuthenticationToken(username, null, convertToAuthorities(authorities));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            catch (Exception ex)
            {
                throw new BadCredentialsException("Invalid Token Provided!");
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException
    {
        List<String>    paths   =   Arrays.asList(JWTTokenGeneratorFilter.LOGIN_API, JWTTokenGeneratorFilter.REGISTER_API);
        return paths.contains(request.getServletPath());
    }

    private static Collection<? extends GrantedAuthority> convertToAuthorities(String authString)
    {
        if (authString == null || authString.isEmpty())
        {
            return null;
        }

        String[]    auths   = authString.contains(",") ? authString.split(",") : new String[] {authString};
        return Arrays.stream(auths).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
}
