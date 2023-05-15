package org.sid.stp.api.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OptionDto {

    private String value;

    private boolean isCorrect;

    // getters and setters
}
