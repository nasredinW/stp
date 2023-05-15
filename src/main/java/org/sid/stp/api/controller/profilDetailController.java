package org.sid.stp.api.controller;

import lombok.RequiredArgsConstructor;
import org.sid.stp.api.documents.User;
import org.sid.stp.api.dto.FileDto;
import org.sid.stp.api.dto.RegisterDto;
import org.sid.stp.api.repository.UserRepository;
import org.springframework.boot.env.ConfigTreePropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user/profilDetail")
@RequiredArgsConstructor
public class profilDetailController {
    private final UserRepository userRepository;
    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUserPhoto(@PathVariable String id, @RequestBody String file) {
        User user = userRepository.findUsersById(id);
        user.setPhoto(file);
        System.out.println(user);

        userRepository.save(user);
        return ResponseEntity.ok("photo aded");

    }
}
