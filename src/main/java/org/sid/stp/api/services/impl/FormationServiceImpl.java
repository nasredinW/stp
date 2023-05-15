package org.sid.stp.api.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.sid.stp.api.documents.Comment;
import org.sid.stp.api.documents.Enum.EFormType;
import org.sid.stp.api.documents.User;
import org.sid.stp.api.documents.formation.CommentDto;
import org.sid.stp.api.documents.formation.Formation;
import org.sid.stp.api.dto.CourDto;
import org.sid.stp.api.repository.CommentRepository;
import org.sid.stp.api.repository.FormationRepository;
import org.sid.stp.api.repository.UserRepository;
import org.sid.stp.api.services.FormationService;
import org.sid.stp.api.services.exceptions.FormationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormationServiceImpl implements FormationService {
    private final FormationRepository formationRepository;
    private final UserRepository userRepository;
private final CommentRepository commentRepository;
   
    @Override
    public void AddCour(CourDto courDto) throws FormationException {
        LocalDate currentDate = LocalDate.now();

       try {
           if ( courDto.getType()== EFormType.cour){
               System.out.println("Start Cour Creation:");
               Formation nwCour= Formation.builder()
                       .title(courDto.getTitle())
                       .description(courDto.getDescription())
                       .link(courDto.getLink())
                       .pdf(courDto.getPdf())
                       .photo(courDto.getPhoto())
                       .groupName(courDto.getGroupName())
                       .type(courDto.getType())
                       .dateCreation(currentDate)
                       .userId(courDto.getUserId())
                       .quizId(courDto.getQuizId())
                       .build();
               formationRepository.save(nwCour);
               System.out.println(nwCour);

           }else if(courDto.getType() == EFormType.session) {
               int duree = Integer.parseInt(courDto.getDuree());
               System.out.println("Start Web Binaire Session Creation:");
               Formation nwSession= Formation.builder()
                       .title(courDto.getTitle())
                       .description(courDto.getDescription())
                       .link(courDto.getLink())
                       .pdf(courDto.getPdf())
                       .photo(courDto.getPhoto())
                       .groupName(courDto.getGroupName())
                       .type(courDto.getType())
                       .dateCreation(currentDate)
                       .userId(courDto.getUserId())
                       .quizId(courDto.getQuizId())
                       .dateSe(courDto.getDateSe())
                       .duree(duree)
                       .meetlink(courDto.getMeetlink())
                       .build();
               formationRepository.save(nwSession);
               System.out.println(nwSession);

           }
       }catch ( Exception e ){
          throw  new FormationException("formation creation rise error check your service ");

       }

    }

    @Override
    public List<Comment> getCommentsForFormation(String formationId) {
        List<Comment> comments = formationRepository.findAllCommentsById(formationId);
        System.out.println(comments);
        return comments;
    }
    @Override
    public Formation addCommentToFormation(String formationId, CommentDto commentDto) {
        Formation formation = formationRepository.findFormationById(formationId);
        User user =userRepository.findUserByIdUser(commentDto.getAuthorId());
        System.out.println(user);
        Comment comment = Comment.builder()
                .text(commentDto.getText())
                .authorId(user.getIdUser())
                .dateCreation(LocalDate.now())
                .build();
         List<Comment> newCmt= new ArrayList<>();
              newCmt.add(comment) ;
        this.commentRepository.save(comment);
              formation.getComments().add(comment);
        formationRepository.save(formation);

        return formation;
    }
}

