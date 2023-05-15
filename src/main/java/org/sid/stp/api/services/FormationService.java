package org.sid.stp.api.services;


import org.sid.stp.api.documents.Comment;
import org.sid.stp.api.documents.formation.CommentDto;
import org.sid.stp.api.documents.formation.Formation;
import org.sid.stp.api.dto.CourDto;
import org.sid.stp.api.services.exceptions.FormationException;

import java.util.List;

public interface FormationService {
    void AddCour(CourDto courDto) throws FormationException;
    List<Comment> getCommentsForFormation(String formationId);
    Formation addCommentToFormation(String formationId, CommentDto commentDto);



}
