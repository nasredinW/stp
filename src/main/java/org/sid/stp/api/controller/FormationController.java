package org.sid.stp.api.controller;


import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.sid.stp.api.documents.Comment;
import org.sid.stp.api.documents.LoadFile;
import org.sid.stp.api.documents.formation.CommentDto;
import org.sid.stp.api.documents.formation.Formation;
import org.sid.stp.api.dto.CourDto;
import org.sid.stp.api.dto.FileDto;
import org.sid.stp.api.repository.FormationRepository;
import org.sid.stp.api.services.FileService;
import org.sid.stp.api.services.FormationService;
import org.sid.stp.api.services.exceptions.FormationException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/SkillUp/Formation")
@RequiredArgsConstructor
public class FormationController {
    private final FormationService formationService;
    private final FormationRepository formationRepository;
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations operations;
    private final FileService fileService;


    @PostMapping(value = "/formation")
    public ResponseEntity<?> SaveFormation(@RequestBody CourDto cour) throws FormationException {
        if (!(cour == null)) {
            formationService.AddCour(cour);
            return ResponseEntity.ok("formation ajouter avec success");
        }
        return new ResponseEntity<>("Bad Request try again", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/formation")
    public ResponseEntity<List<Formation>> getAllFormation(){

        return ResponseEntity.ok(this.formationRepository.findAll());
    }


    @PostMapping("/upload")
    public ResponseEntity<FileDto> upload(@RequestParam("file") MultipartFile file) throws Exception {
        String photoUrl = fileService.uploadFile(file);
        FileDto fileUrl = FileDto.builder().FileUrl(photoUrl).build();
        return ResponseEntity.ok(fileUrl);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        LoadFile loadFile = fileService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + "\"")
                .body(new ByteArrayResource(loadFile.getFile()));
    }

    @GetMapping("file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        if (gridFSFile == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] data = IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(gridFSFile.getMetadata().get("contentType").toString()));
        headers.setContentLength(data.length);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(gridFSFile.getFilename()).build());
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }


    @PostMapping("/like/{id}")
    public ResponseEntity<?> addLike(@PathVariable String id) {
        Formation forma = formationRepository.findFormationById(id);
        if (forma == null) {
            return ResponseEntity.notFound().build();
        }
        int nbrLike = forma.getNbr_like();
        forma.setNbr_like(nbrLike + 1);
        Formation updatedForma = formationRepository.save(forma);
        return ResponseEntity.ok(updatedForma.getNbr_like());
    }
    @PostMapping("/{formationId}/comment")
    public ResponseEntity<?> addCommentToFormation(@PathVariable String formationId, @RequestBody CommentDto commentDto) {
        Formation updatedFormation = formationService.addCommentToFormation(formationId, commentDto);
        return ResponseEntity.ok(updatedFormation);
    }

    @GetMapping("/formations/{id}/comments")
    public ResponseEntity<List<Comment>> getCommentsForFormation(@PathVariable String id) {
        return ResponseEntity.ok(this.formationService.getCommentsForFormation(id));
    }




}



