package org.sid.stp.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sid.stp.api.documents.Enum.ERole;
import org.sid.stp.api.documents.User;
import org.sid.stp.api.documents.token.Token;
import org.sid.stp.api.documents.token.TokenType;
import org.sid.stp.api.dto.AuthDto;
import org.sid.stp.api.dto.AuthResDto;
import org.sid.stp.api.dto.ReguDto;
import org.sid.stp.api.repository.TokenRepository;
import org.sid.stp.api.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResDto register(ReguDto request) {
        System.out.println("start register");
        var user = User.builder()


                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .roleName(ERole.ROLE_USER)
                .build();
        System.out.println(user);
        var savedUser = repository.save(user);
        System.out.println("user saved us succsufly ");
        var jwtToken = jwtService.generateToken(user);
        System.out.println(jwtToken);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthResDto.builder()
                .AccessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResDto authenticate(AuthDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        System.out.println("authenticat");
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        user.setConnected(true);
        System.out.println(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthResDto.builder()
                .AccessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(user)
                .message("welcome to skill up")
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllByUser_IdAndExpiredIsFalseAndRevokedIsFalse(user.getIdUser());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByUsername(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthResDto.builder()
                        .AccessToken(accessToken)
                        .refreshToken(refreshToken)
                        .message("refresh-token")
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
