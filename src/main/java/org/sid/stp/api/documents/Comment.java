package org.sid.stp.api.documents;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;
    private String text;
    private LocalDate dateCreation = LocalDate.now();
    private String authorId;

    // getters and setters

}
