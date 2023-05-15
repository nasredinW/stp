package org.sid.stp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.stp.api.documents.User;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupRes {
    private List<String> groupNames;
    private List<List<User>> users;
    private String Message;
}
