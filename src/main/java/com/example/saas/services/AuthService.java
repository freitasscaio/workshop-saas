
package com.example.saas.services;

import java.time.Instant;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.saas.dto.LoginRequestDTO;
import com.example.saas.dto.LoginResponseDTO;
import com.example.saas.models.AppUser;
import com.example.saas.repositories.AppUserRepository;

@Service
public class AuthService {

    private static final long TOKEN_VALIDITY_SECONDS = 3600;

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public AuthService(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            JwtEncoder jwtEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        AppUser user = appUserRepository.findByEmail(email)
                .orElseThrow(this::invalidCredentials);

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw invalidCredentials();
        }

        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(TOKEN_VALIDITY_SECONDS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("saas-api")
                .subject(user.getEmail())
                .issuedAt(now)
                .expiresAt(expiresAt)
                .claim("userId", user.getId())
                .claim("workshopId", user.getWorkshop().getId())
                .claim("role", user.getRole())
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256)
                .build();

        String token = jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims))
                .getTokenValue();

        return new LoginResponseDTO(
                token,
                "Bearer",
                TOKEN_VALIDITY_SECONDS);
    }

    private ResponseStatusException invalidCredentials() {
        return new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "E-mail ou senha inválidos.");
    }
}
