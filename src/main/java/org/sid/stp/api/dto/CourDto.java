package org.sid.stp.api.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.stp.api.documents.Enum.EFormType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourDto {
    private String title;
    private String description;
    private String link;
    private String pdf;
    private String photo;
    private String GroupName;
    private EFormType type;
    private String userId;
    private String quizId;
    private String meetlink;
    private String duree;
    private String dateSe;
    private int nbr_like;


}
