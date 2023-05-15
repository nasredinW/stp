package org.sid.stp.api.documents.formation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Option {

    private String value;

    private boolean isCorrect;

    // constructors, getters, and setters
}
