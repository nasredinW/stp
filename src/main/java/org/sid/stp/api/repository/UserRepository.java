package org.sid.stp.api.repository;

import org.sid.stp.api.documents.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findByUsername(String username);
    String findPasswordByUsername(String username);
    User findUsersById(String id);
    User findUserByIdUser(String idUser);

}
