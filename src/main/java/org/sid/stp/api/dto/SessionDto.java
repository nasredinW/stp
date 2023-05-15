package org.sid.stp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {

    private String title;
    private String description;
    private String photo;
    private String link;

}
