package com.example.affiliatia.serviceimpl;

import com.example.affiliatia.Entity.Role;
import com.example.affiliatia.Entity.User;
import com.example.affiliatia.Repository.UserRepository;
import com.example.affiliatia.dto.request.LoginRequest;
import com.example.affiliatia.dto.request.RegisterRequest;
import com.example.affiliatia.dto.response.AuthResponse;
import com.example.affiliatia.security.JwtService;
import com.example.affiliatia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        User user = User.builder()
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(Role.CLIENT)
                .build();

        userRepository.save(user);

        return new AuthResponse("Utilisateur créé avec succès");
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.email());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}