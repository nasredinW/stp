package org.sid.stp.api.services;

import org.sid.stp.api.documents.Groups;
import org.sid.stp.api.documents.User;
import org.sid.stp.api.dto.RegisterDto;

import java.util.List;

public interface UserService {

     User AddUser(RegisterDto user);
     List<User> getAllUsers();
     User updateUser(String username, RegisterDto registerDto);
   void deleteUser(String id);

}
