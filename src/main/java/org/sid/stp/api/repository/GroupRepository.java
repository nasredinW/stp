package org.sid.stp.api.repository;

import org.sid.stp.api.documents.Groups;
import org.sid.stp.api.documents.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.stream.Collectors;

public interface GroupRepository extends MongoRepository<Groups,String> {
Groups findGroupsByGroupName(String groupName);
Groups findGroupsByIdGroup(String id);


    default List<String> findAllGroupNames() {
        List<Groups> groups = findAll();
        return groups.stream()
                .map(Groups::getGroupName)
                .collect(Collectors.toList());
    }
    default List<List<User>> findAllUserNames() {
        List<Groups> groups = findAll();
        return groups.stream()
                .map(Groups::getUsers)
                .collect(Collectors.toList());
    }

}
