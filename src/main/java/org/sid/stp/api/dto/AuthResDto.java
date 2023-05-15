package org.sid.stp.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.stp.api.documents.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResDto {

    @JsonProperty("AccessToken")
    private String AccessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private User user;
   private String username;
    private String message;


}
