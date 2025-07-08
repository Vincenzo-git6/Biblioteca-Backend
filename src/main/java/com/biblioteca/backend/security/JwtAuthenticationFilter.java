package com.biblioteca.backend.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//prende dal token provider le informazioni
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        // prendi il token JWT dalla request
        String token = getJwtFromRequest(request);

        // valida il token 
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            // Get username from token
            String username = tokenProvider.getUsernameFromJWT(token);
            
            // crea oggetto per autenticazione
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                username, null, tokenProvider.getAuthoritiesFromJWT(token)
            );
            
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            
            // imposta autenticazione nel Secutity Context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
