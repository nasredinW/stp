package org.sid.stp.api.controller;

import lombok.RequiredArgsConstructor;
import org.sid.stp.api.documents.formation.Option;
import org.sid.stp.api.documents.formation.Question;
import org.sid.stp.api.documents.formation.Quiz;
import org.sid.stp.api.dto.quiz.OptionDto;
import org.sid.stp.api.dto.quiz.QuestionDto;
import org.sid.stp.api.dto.quiz.QuizDto;
import org.sid.stp.api.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
public class QuizController {


    private final QuizService quizService;

    @PostMapping("/add")
    public ResponseEntity<Quiz> addQuiz(@RequestBody QuizDto quizDto) {
        Quiz quiz = new Quiz();
        List<Question> questions = new ArrayList<>();
        for (QuestionDto questionDto : quizDto.getQuestions()) {
            Question question = new Question();
            question.setQuestion(questionDto.getQuestion());
            List<Option> options = new ArrayList<>();
            for (OptionDto optionDto : questionDto.getOptions()) {
                Option option = new Option();
                option.setValue(optionDto.getValue());
                option.setCorrect(optionDto.isCorrect());
                options.add(option);
            }
            question.setOptions(options);
            questions.add(question);
        }
        quiz.setQuestions(questions);
        Quiz createdQuiz = quizService.createQuiz(quiz);
        return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
    }
}

