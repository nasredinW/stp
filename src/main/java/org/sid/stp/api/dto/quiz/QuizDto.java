package org.sid.stp.api.dto.quiz;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class QuizDto {

    private List<QuestionDto> questions;

    // getters and setters
}