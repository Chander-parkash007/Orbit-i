package com.orbit.backend.service;


import com.orbit.backend.dto.AuthResponse;
import com.orbit.backend.dto.LoginRequest;
import com.orbit.backend.dto.RegisterRequest;
import com.orbit.backend.entities.ClientUser;
import com.orbit.backend.repository.ClientUserRepository;
import com.orbit.backend.security.JwtUtill;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClientUserRepository clientUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtill jwtUtil;

    public String register(RegisterRequest request) {
        // Check if email already exists
        if (clientUserRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        ClientUser user = new ClientUser();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhone(request.phone());
        user.setCompany(request.company());
        // approved = false by default — admin must approve

        clientUserRepository.save(user);
        return "Registration successful. Please wait for admin approval.";
    }

    public AuthResponse login(LoginRequest request) {
        // This throws if credentials are wrong
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // If we reach here credentials are correct
        ClientUser user = clientUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check approval — isEnabled() returns false if not approved
        if (!user.isEnabled()) {
            throw new RuntimeException("Account not yet approved by admin");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(
                token,
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
