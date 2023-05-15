package org.sid.stp.api.dto;

import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.stp.api.documents.Enum.EProfile;
import org.sid.stp.api.documents.Enum.ERole;
import org.sid.stp.api.documents.Enum.Estatu;
import org.sid.stp.api.documents.Groups;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    private String username;

    private String groupName;
    private EProfile profileName;




}
