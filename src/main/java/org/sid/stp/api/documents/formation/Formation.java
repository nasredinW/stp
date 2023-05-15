package org.sid.stp.api.documents.formation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.stp.api.documents.Comment;
import org.sid.stp.api.documents.Enum.EFormType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "formation")
public class Formation {
    @Id
    private String id;
    private String title;
    private String description;
    private String link;
    private LocalDate dateCreation = LocalDate.now();
    private int duree;
    private EFormType type;
    private String photo;
    private String pdf;
    private int nbr_like;
    private String meetlink;
    private String dateSe;
    private String userId;
    private String quizId;
    private String groupName;
    @DBRef
    private List<Comment> comments;


}
