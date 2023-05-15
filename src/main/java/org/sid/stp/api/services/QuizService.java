package org.sid.stp.api.services;

import lombok.RequiredArgsConstructor;
import org.sid.stp.api.documents.formation.Quiz;
import org.sid.stp.api.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {


    private final QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(String id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        return quizOptional.orElse(null);
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(String id, Quiz quiz) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if (quizOptional.isPresent()) {
            Quiz existingQuiz = quizOptional.get();
            existingQuiz.setQuestions(quiz.getQuestions());
            return quizRepository.save(existingQuiz);
        }
        return null;
    }

    public void deleteQuiz(String id) {
        quizRepository.deleteById(id);
    }
}

