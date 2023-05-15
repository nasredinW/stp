package org.sid.stp.api.services.impl;

import lombok.RequiredArgsConstructor;
import org.sid.stp.api.documents.Enum.EProfile;
import org.sid.stp.api.documents.Enum.ERole;
import org.sid.stp.api.documents.Enum.Estatu;
import org.sid.stp.api.documents.Groups;
import org.sid.stp.api.documents.User;
import org.sid.stp.api.dto.RegisterDto;
import org.sid.stp.api.repository.GroupRepository;
import org.sid.stp.api.repository.UserRepository;
import org.sid.stp.api.services.UserService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;


    @Override
    public User AddUser(RegisterDto registerDto) {

        String groupName = registerDto.getGroupName();
        EProfile profile = registerDto.getProfileName();
        ERole role = null;
        Estatu status= null;
        if (profile == EProfile.ADM) {
            role = ERole.ROLE_ADMIN;
            status= Estatu.SKILLUP_ADMIN;
        } else if (profile == EProfile.US) {
            role = ERole.ROLE_USER;
            status= Estatu.TEAM_MEMBER;
        } else if (profile == EProfile.EXP) {
            role = ERole.ROLE_EXPERT;
            status=Estatu.TEAM_EXPERT;
        }else if(profile == EProfile.MOD){
            role = ERole.ROLE_MODERATOR;
            status=Estatu.TEAM_MANAGER;
        }
        Random obj = new Random();
        int nbr =0;
        for (int i = 0; i < 10; i++){
            nbr = obj. nextInt(100);
            System.out.println("Nombre aléatoire : " + nbr);
        }

        User addedUser = User.builder()
                .idUser("skill"+nbr)
                .username(registerDto.getUsername())
                .profileName(registerDto.getProfileName())
                .roleName(role)
                .statu(status)
                .groupName(registerDto.getGroupName())
                .build();
        userRepository.save(addedUser);
        // check if group exists
        Groups group = groupRepository.findGroupsByGroupName(groupName);
        if (group == null) {
            // create new group if not found
            group = Groups.builder()
                    .groupName(groupName)
                    .users(new ArrayList<>())
                    .build();}
        // add user to group
        List<User> users = group.getUsers();
        users.add(addedUser);
        group.setUsers(users);
        groupRepository.save(group);
        return addedUser;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(String username, RegisterDto registerDto) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (!existingUser.isPresent()) {
            try {
                throw new ChangeSetPersister.NotFoundException();
            } catch (ChangeSetPersister.NotFoundException e) {
                throw new RuntimeException("user not found in ouer data base imposible to update");
            }
        }
        User user = existingUser.get();

        // Update user fields if they are not null
        if (registerDto.getGroupName() != null) {
            String groupName = registerDto.getGroupName();
            Groups group = groupRepository.findGroupsByGroupName(groupName);
            if (group == null) {
                group = Groups.builder()
                        .groupName(groupName)
                        .users(new ArrayList<>())
                        .build();
            }

            List<User> groupUsers = group.getUsers();
            if (!groupUsers.contains(user)) {
                groupUsers.add(user);
            }
            group.setUsers(groupUsers);
            groupRepository.save(group);

            user.setGroupName(groupName);
        }

        EProfile profile = registerDto.getProfileName();
        if (registerDto.getProfileName() != null) {

            ERole role = null;
            if (profile == EProfile.ADM) {
                role = ERole.ROLE_ADMIN;
            } else if (profile == EProfile.US) {
                role = ERole.ROLE_USER;
            } else if (profile == EProfile.EXP) {
                role = ERole.ROLE_EXPERT;
            }else if(profile == EProfile.MOD){
                role = ERole.ROLE_MODERATOR;
            }
            user.setRoleName(role);
            user.setProfileName(registerDto.getProfileName());
        }


        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }


}
