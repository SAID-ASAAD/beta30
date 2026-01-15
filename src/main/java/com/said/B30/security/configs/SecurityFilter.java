package com.said.B30.security.configs;

import com.said.B30.infrastructure.repositories.UserRepository;
import com.said.B30.security.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    
    private static final String AUTH_COOKIE_NAME = "B30_AUTH_TOKEN";
    private static final int TOKEN_EXPIRATION_SECONDS = 60 * 15;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        
        if(token != null){
            try {
                var name = tokenService.validateToken(token);
                UserDetails user = userRepository.findByName(name);

                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // Sliding Session: Renova o token se estiver válido
                    String newToken = tokenService.generateToken(user);
                    addAuthCookie(response, newToken, TOKEN_EXPIRATION_SECONDS);
                }
            } catch (RuntimeException e) {
                // Token inválido ou expirado: Limpa o contexto e o cookie
                SecurityContextHolder.clearContext();
                addAuthCookie(response, null, 0);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader != null) {
            return authHeader.replace("Bearer ", "");
        }

        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                .filter(cookie -> AUTH_COOKIE_NAME.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
        }
        return null;
    }
    
    private void addAuthCookie(HttpServletResponse response, String token, int maxAge) {
        Cookie cookie = new Cookie(AUTH_COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
