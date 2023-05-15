package org.sid.stp.api.repository;

import org.sid.stp.api.documents.Comment;
import org.sid.stp.api.documents.formation.Formation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public interface FormationRepository extends MongoRepository<Formation,String> {
    @Query(value = "{'_id': ?0}", fields = "{'comments': 1}")
    @Override
    Optional<Formation> findById(String s);

    Optional<Formation>findFormationByTitle(String title);
    Formation findFormationById(String id);

    Optional<Formation> findFormationCommentsById(String id);

    default List<Comment> findAllCommentsById(String id) {
        Optional<Formation> optionalFormation = findFormationCommentsById(id);
        return optionalFormation.map(Formation::getComments).orElse(Collections.emptyList());
    }
}
