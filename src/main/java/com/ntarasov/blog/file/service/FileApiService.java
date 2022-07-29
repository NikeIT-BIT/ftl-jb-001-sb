package com.ntarasov.blog.file.service;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.ntarasov.blog.base.api.reqest.SearchRequest;
import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.file.mapping.FileMapping;
import com.ntarasov.blog.file.exception.FileExistException;
import com.ntarasov.blog.file.exception.FileNotExistException;
import com.ntarasov.blog.file.model.FileDoc;
import com.ntarasov.blog.file.repository.FileRepository;
import com.ntarasov.blog.user.exception.UserNotExistException;
import com.ntarasov.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class FileApiService {
//<---------------------------------FINAL------------------------------------------------->
    private final FileRepository fileRepository;
    private final MongoTemplate mongoTemplate;
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;
    private final UserRepository userRepository;

//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    public Optional<FileDoc> findById(ObjectId id){
            return fileRepository.findById(id);
    }

//<---------------------------------------------------------------------------------->
    public InputStream downloadById(ObjectId id) throws ChangeSetPersister.NotFoundException, IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        if(file == null) throw  new ChangeSetPersister.NotFoundException();
        return gridFsOperations.getResource(file).getInputStream();
    }


//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    public FileDoc create(MultipartFile multipartFile, ObjectId ownerId) throws FileExistException, IOException, UserNotExistException {
        if(userRepository.findById(ownerId).isEmpty()) throw new UserNotExistException();

        DBObject metaData = new BasicDBList();
        metaData.put("type", multipartFile.getContentType());
        metaData.put("title", multipartFile.getOriginalFilename());

        ObjectId id = gridFsTemplate.store(
                multipartFile.getInputStream(),
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                metaData
        );

        FileDoc fileDoc = FileDoc.builder()
                .id(id)
                .title(multipartFile.getOriginalFilename())
                .ownerId(ownerId)
                .contentType(multipartFile.getContentType())
                .build();

        fileRepository.save(fileDoc);
        return fileDoc;
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    public SearchResponse<FileDoc> search(SearchRequest request){

        Criteria criteria = new Criteria();

        if(request.getQuery()!= null && !Objects.equals(request.getQuery(), "")){
            criteria = criteria.orOperator(

                    Criteria.where("title").regex(request.getQuery(),"i")

            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, FileDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<FileDoc> fileDocs = mongoTemplate.find(query, FileDoc.class);
        return  SearchResponse.of(fileDocs, count);
    }


//<---------------------------------УДАЛЕНИЕ------------------------------------------------->
    public void delete(ObjectId id){
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
        fileRepository.deleteById(id);
    }
}
