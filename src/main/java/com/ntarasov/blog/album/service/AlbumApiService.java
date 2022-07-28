package com.ntarasov.blog.album.service;

import com.ntarasov.blog.base.api.reqest.SearchRequest;
import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.album.api.request.AlbumRequest;
import com.ntarasov.blog.album.mapping.AlbumMapping;
import com.ntarasov.blog.album.exception.AlbumExistException;
import com.ntarasov.blog.album.exception.AlbumNotExistException;
import com.ntarasov.blog.album.model.AlbumDoc;
import com.ntarasov.blog.album.repository.AlbumRepository;
import com.ntarasov.blog.user.exception.UserNotExistException;
import com.ntarasov.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AlbumApiService {
//<---------------------------------FINAL------------------------------------------------->
    private final AlbumRepository albumRepository;
    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;

//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    public Optional<AlbumDoc> findById(ObjectId id){
            return albumRepository.findById(id);
    }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    public AlbumDoc create(AlbumRequest request) throws AlbumExistException, UserNotExistException {
        if(userRepository.findById(request.getOwnerId()).isEmpty()) throw new UserNotExistException();

        AlbumDoc albumDoc = AlbumMapping.getInstance().getRequest().convert(request);
        albumRepository.save(albumDoc);
        return albumDoc;
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    public SearchResponse<AlbumDoc> search(SearchRequest request){

        Criteria criteria = new Criteria();

        if(request.getQuery()!= null && !Objects.equals(request.getQuery(), "")){
            criteria = criteria.orOperator(

                    Criteria.where("title").regex(request.getQuery(),"i")
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, AlbumDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<AlbumDoc> albumDocs = mongoTemplate.find(query, AlbumDoc.class);
        return  SearchResponse.of(albumDocs, count);
    }

//<---------------------------------ОБНОВЛЕНИЕ------------------------------------------------->
    public AlbumDoc update(AlbumRequest request) throws AlbumNotExistException {
        Optional<AlbumDoc> albumDocOptional = albumRepository.findById(request.getId());
        if (albumDocOptional.isEmpty()) throw new AlbumNotExistException();

        AlbumDoc oldDoc = albumDocOptional.get();

        AlbumDoc albumDoc = AlbumMapping.getInstance().getRequest().convert(request);
        albumDoc.setId(request.getId());
        albumDoc.setOwnerId(oldDoc.getOwnerId());
        albumRepository.save(albumDoc);
        return albumDoc;
    }

//<---------------------------------УДАЛЕНИЕ------------------------------------------------->
    public void delete(ObjectId id){
        albumRepository.deleteById(id);
    }
}
