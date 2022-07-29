package com.ntarasov.blog.photo.service;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.ntarasov.blog.album.repository.AlbumRepository;
import com.ntarasov.blog.base.api.reqest.SearchRequest;
import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.photo.api.request.PhotoRequest;
import com.ntarasov.blog.photo.api.request.PhotoSearchRequest;
import com.ntarasov.blog.photo.mapping.PhotoMapping;
import com.ntarasov.blog.photo.exception.PhotoExistException;
import com.ntarasov.blog.photo.exception.PhotoNotExistException;
import com.ntarasov.blog.photo.model.PhotoDoc;
import com.ntarasov.blog.photo.repository.PhotoRepository;
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
import java.util.function.ObjIntConsumer;

@Service
@RequiredArgsConstructor

public class PhotoApiService {
//<---------------------------------FINAL------------------------------------------------->
    private final PhotoRepository photoRepository;
    private final MongoTemplate mongoTemplate;
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;

//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    public Optional<PhotoDoc> findById(ObjectId id){
            return photoRepository.findById(id);
    }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    public PhotoDoc create(MultipartFile file, ObjectId ownerId, ObjectId albumId) throws PhotoExistException, PhotoNotExistException, UserNotExistException, IOException {
        if(albumRepository.findById(albumId).isEmpty()) throw new PhotoNotExistException();
        if(userRepository.findById(ownerId).isEmpty()) throw new UserNotExistException();

        DBObject metaData = new BasicDBList();
        metaData.put("type", file.getContentType());
        metaData.put("title", file.getOriginalFilename());

        ObjectId id = gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType(),
                metaData
        );

        PhotoDoc photoDoc = PhotoDoc.builder()
                .id(id)
                .albumId(albumId)
                .ownerId(ownerId)
                .title(file.getOriginalFilename())
                .contentType(file.getContentType())
                .build();

        photoRepository.save(photoDoc);
        return photoDoc;
    }

    public InputStream downloadById(ObjectId id) throws ChangeSetPersister.NotFoundException, IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        if(file == null) throw  new ChangeSetPersister.NotFoundException();
        return gridFsOperations.getResource(file).getInputStream();
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    public SearchResponse<PhotoDoc> search(PhotoSearchRequest request){

        Criteria criteria = Criteria.where("albumId").is(request.getAlbumId());

        if(request.getQuery()!= null && !Objects.equals(request.getQuery(), "")){
            criteria = criteria.orOperator(

                    Criteria.where("titlw").regex(request.getQuery(),"i")
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, PhotoDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<PhotoDoc> photoDocs = mongoTemplate.find(query, PhotoDoc.class);
        return  SearchResponse.of(photoDocs, count);
    }

//<---------------------------------ОБНОВЛЕНИЕ------------------------------------------------->
    public PhotoDoc update(PhotoRequest request) throws PhotoNotExistException {
        Optional<PhotoDoc> photoDocOptional = photoRepository.findById(request.getId());
        if (photoDocOptional.isEmpty()) throw new PhotoNotExistException();

        PhotoDoc oldDoc = photoDocOptional.get();

        PhotoDoc photoDoc = PhotoMapping.getInstance().getRequest().convert(request);
        photoDoc.setId(request.getId());
        photoDoc.setAlbumId(oldDoc.getAlbumId());
        photoDoc.setOwnerId(oldDoc.getOwnerId());
        photoDoc.setContentType(oldDoc.getContentType());
        photoRepository.save(photoDoc);
        return photoDoc;
    }

//<---------------------------------УДАЛЕНИЕ------------------------------------------------->
    public void delete(ObjectId id){
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
        photoRepository.deleteById(id);
    }
}
