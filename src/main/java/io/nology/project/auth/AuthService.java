package io.nology.project.auth;

import io.nology.project.auth.dtos.LoginRequestDTO;
import io.nology.project.common.exception.UnauthorisedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public String login(LoginRequestDTO data){
        try{
            Authentication authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(data.getEmail(),data.getPassword()));
            AppUserDetails principal = (AppUserDetails) authentication.getPrincipal();
            return jwtService.generateAccessToken(principal.getAppUser());
        } catch(AuthenticationException ex){
            throw new UnauthorisedException("Invalid email or password");
        }
    }
}

