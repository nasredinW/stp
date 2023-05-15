package org.sid.stp.api.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    private String question;

    private List<OptionDto> options;

    // getters and setters
}

