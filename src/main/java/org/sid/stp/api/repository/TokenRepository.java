package org.sid.stp.api.repository;

import org.sid.stp.api.documents.token.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {
    List<Token> findAllByUser_IdAndExpiredIsFalseAndRevokedIsFalse(String userId);
    Optional<Token> findByToken(String token);
}
