package org.sid.stp.api.documents.formation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {

    private String question;

    private List<Option> options;

    // constructors, getters, and setters
}
