package io.nology.project.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final Logger log = LogManager.getLogger(JwtFilter.class);
    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public record JwtPrincipal(Long userId, Role role){

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String token = extractToken(request);
        if(token == null || token.isBlank()){
            filterChain.doFilter(request, response);
            return;
        }
        try{
            Jws<Claims> claims = jwtService.parse(token);
            Long userId = Long.valueOf(claims.getPayload().getSubject());
            Role role = Role.valueOf(claims.getPayload().get("role", String.class));
            JwtPrincipal principal = new JwtPrincipal(userId, role);
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch(JwtException | IllegalArgumentException e){
            log.warn("Rejected JWT: {}" , e.getMessage());
        }

        filterChain.doFilter(request,response);
    }

    private String extractToken(HttpServletRequest request){
//        String cookieToken = extractCookie(request, "access_token");
//        if(cookieToken != null){
//            return cookieToken;
//        }
        return extractBearerHeader(request);
    }

    private String extractCookie(HttpServletRequest request, String name){
        if(request.getCookies() == null){
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    private String extractBearerHeader(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }
}
