package org.sid.stp.api.services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.sid.stp.api.documents.LoadFile;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations operations;


    public String uploadFile(MultipartFile upload) throws Exception{


        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", upload.getSize());
        metadata.put("contentType",upload.getContentType());
        metadata.put("fileSize",upload.getSize());

        Object fileID = gridFsTemplate.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata);

        return fileID.toString();
    }

    public ResponseEntity<byte[]> getFile(String id) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        if (gridFSFile == null) {

            return ResponseEntity.notFound().build();
        }
        byte[] data = IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream());
        HttpHeaders headers = new HttpHeaders();
        assert gridFSFile.getMetadata() != null;
        headers.setContentType(MediaType.valueOf(gridFSFile.getMetadata().get("contentType").toString()));
        headers.setContentLength(data.length);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(gridFSFile.getFilename()).build());
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }






    public LoadFile downloadFile(String id) throws IOException {

        GridFSFile gridFSFile = gridFsTemplate.findOne( new Query(Criteria.where("_id").is(id)) );

        LoadFile loadFile = new LoadFile();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            loadFile.setFilename( gridFSFile.getFilename() );

            loadFile.setFileType( gridFSFile.getMetadata().get("contentType").toString() );

            loadFile.setFileSize( gridFSFile.getMetadata().get("fileSize").toString() );

            loadFile.setFile( IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()) );
        }

        return loadFile;
    }
}
