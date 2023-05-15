package org.sid.stp.api.controller;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.sid.stp.api.documents.Enum.EProfile;
import org.sid.stp.api.documents.User;
import org.sid.stp.api.dto.RegisterDto;
import org.sid.stp.api.dto.UserInfoDto;
import org.sid.stp.api.repository.UserRepository;
import org.sid.stp.api.services.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/SkillUp/admin")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl service;
    private final UserRepository userRepository;

    @PostMapping("/AddUser")
    public ResponseEntity<?> AddUser(@RequestBody RegisterDto user) {

        return ResponseEntity.ok(service.AddUser(user));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> DeleteUserById(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            Optional<User> existingUser = userRepository.findById(id);
            if (existingUser.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id);
            }

            userRepository.delete(existingUser.get());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id: " + id);
        }
    }

    @PutMapping("/users/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody RegisterDto registerDto) {
        User updatedUser = service.updateUser(username, registerDto);
        return ResponseEntity.ok(updatedUser);
    }


    @PostMapping(value = "/users/batch", consumes = "multipart/form-data")
    public ResponseEntity<String> batchAddUsers(@RequestParam("file") MultipartFile file) {
        List<RegisterDto> registerDtoList = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length != 4) {
                    throw new IllegalArgumentException("Invalid line format: " + line);
                }
                String username = parts[0];
                EProfile profileName = EProfile.valueOf(parts[1]);
                String groupName = parts[2];


                RegisterDto registerDto = new RegisterDto();
                registerDto.setUsername(username);
                registerDto.setProfileName(profileName);
                registerDto.setGroupName(groupName);


                registerDtoList.add(registerDto);
            }
            reader.close();
            for (RegisterDto user : registerDtoList) {
                service.AddUser(user);
            }
            System.out.println("all users adeded");
            return ResponseEntity.ok("Users added successfully");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to read file", e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUser(@PathVariable String id){
        User user = userRepository.findUserByIdUser(id);
        System.out.println(user);
        if (!(user==null)) {
            UserInfoDto userInfoDto = UserInfoDto.builder()
                    .photo(user.getPhoto())
                    .username(user.getUsername())
                    .groupName(user.getGroupName())
                    .build();
            return ResponseEntity.ok(userInfoDto);
        }
        return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);

    }
}





