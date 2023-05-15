package org.sid.stp.api.controller;

import lombok.RequiredArgsConstructor;
import org.sid.stp.api.documents.Groups;
import org.sid.stp.api.documents.User;
import org.sid.stp.api.dto.GroupRes;
import org.sid.stp.api.repository.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/SkillUp/admin/Group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupRepository groupRepository;
    @GetMapping("/groups")
    public ResponseEntity<List<Groups>> listGroup()
    {
        return ResponseEntity.ok(groupRepository.findAll());
    }
    @PostMapping("/groups")
    public ResponseEntity<Groups> addGroup(@RequestBody Groups groups){
        Groups NewGroup =  Groups.builder()
                .idGroup(groups.getIdGroup())
                .groupName(groups.getGroupName())
                .build();
        return ResponseEntity.ok(groupRepository.save(NewGroup));
    }
    @DeleteMapping("/groups/{id}")
    public ResponseEntity<?> deleteGroupById(@PathVariable String id){
        Groups groups = groupRepository.findGroupsByIdGroup(id);
        if (groups.getUsers()==null){
            groupRepository.delete(groups);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Group Contains",HttpStatus.CONFLICT);

    }
    @GetMapping("/groups/nameGroup")
    public ResponseEntity<GroupRes> ListNames(){
        GroupRes groupRes = GroupRes.builder()
                .groupNames(groupRepository.findAllGroupNames())
                .build();
        return ResponseEntity.ok(groupRes);
    }
    @GetMapping("/groups/users/{id}")
    public  ResponseEntity<GroupRes> groupFindListUser(@PathVariable String id)
    {
        Groups groups = groupRepository.findGroupsByIdGroup(id);
        if (!(groups.getUsers()==null)) {

            GroupRes groupRes = GroupRes.builder()
                    .users(groupRepository.findAllUserNames())
                    .build();
           return ResponseEntity.ok(groupRes);


        }
        return  new ResponseEntity("Group vide",HttpStatus.INSUFFICIENT_STORAGE);
    }
}
