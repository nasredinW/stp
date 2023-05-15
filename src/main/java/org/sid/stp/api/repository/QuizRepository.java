package org.sid.stp.api.repository;

import org.sid.stp.api.documents.formation.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends MongoRepository<Quiz, String> {

}
