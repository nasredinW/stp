package org.sid.stp.api.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.stp.api.documents.Enum.EProfile;
import org.sid.stp.api.documents.Enum.ERole;
import org.sid.stp.api.documents.Enum.Estatu;
import org.sid.stp.api.documents.token.Token;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User  implements UserDetails {
    @Id
    private String id;
    private String idUser;
    private String username;
    private String password;
    private  boolean  isConnected= false;
    private String email;
    private String photo;
    private EProfile profileName;
    @Enumerated(EnumType.STRING)
    private  ERole roleName ;
    private Estatu statu;
    private String groupName;

        @DBRef
    private Groups group;
    @DBRef
    private List<Token> tokens;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roleName.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
