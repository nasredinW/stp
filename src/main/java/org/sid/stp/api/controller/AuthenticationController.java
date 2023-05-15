package org.sid.stp.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sid.stp.api.documents.User;
import org.sid.stp.api.dto.AuthDto;
import org.sid.stp.api.dto.AuthResDto;
import org.sid.stp.api.dto.ReguDto;
import org.sid.stp.api.repository.UserRepository;
import org.sid.stp.api.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthResDto> register(
            @RequestBody ReguDto request
    ) {
        System.out.println("start service register");
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthDto request

    ) {

        Optional<User> optionalUser = repository.findByUsername(request.getUsername());



        if ((optionalUser.isPresent()) &&  request.getPassword().isEmpty()&& optionalUser.get().getPassword()== null) {
            return  ResponseEntity.ok(AuthResDto.builder().username(request.getUsername()).message("Password Expired").build());
        }else
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

        @PutMapping("users/{username}")
        public ResponseEntity<Object> updatePassword(@PathVariable String username, @RequestBody String newPassword) {
            Optional<User> optionalUser = repository.findByUsername(username);
            String pwd = repository.findPasswordByUsername(username);
            System.out.println(pwd);

            if (!optionalUser.isPresent()) {
                return new ResponseEntity<>("User not found or password not expired", HttpStatus.NOT_FOUND);
            }


                User user = optionalUser.get();
                user.setPassword(passwordEncoder.encode(newPassword));
                repository.save(user);
                System.out.println(newPassword);

                return ResponseEntity.ok().build();

        }


}

