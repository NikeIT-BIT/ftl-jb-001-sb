package com.ntarasov.blog.user.service;


import com.ntarasov.blog.base.api.reqest.SearchRequest;
import com.ntarasov.blog.base.api.response.SearchResponse;
import com.ntarasov.blog.user.api.request.RegistrationRequest;
import com.ntarasov.blog.user.api.request.UpdateUserRequest;
import com.ntarasov.blog.user.exception.UserExistException;
import com.ntarasov.blog.user.exception.UserNotExistException;
import com.ntarasov.blog.user.model.UserDoc;
import com.ntarasov.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserApiService {
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    public UserDoc registration(RegistrationRequest request) throws UserExistException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserExistException();
        }
        UserDoc userDoc = new UserDoc();
        userDoc.setEmail(request.getEmail());
        userDoc.setPassword(UserDoc.hexPassword(request.getPassword()));
        userDoc = userRepository.save(userDoc);
        return userDoc;
    }

    public Optional<UserDoc> findById(ObjectId id){
        return userRepository.findById(id);
    }
    public SearchResponse<UserDoc> search(SearchRequest request){
        Criteria criteria = new Criteria();
        if(request.getQuery()!= null && !Objects.equals(request.getQuery(), "")){
            criteria = criteria.orOperator(
                    Criteria.where("firstName").regex(request.getQuery(),"i"),
                    Criteria.where("lastName").regex(request.getQuery(),"i"),
                    Criteria.where("email").regex(request.getQuery(),"i")
            );
        }

        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, UserDoc.class);
        query.limit(request.getSize());
        query.skip(request.getSkip());
        List<UserDoc> userDocs = mongoTemplate.find(query, UserDoc.class);
        return  SearchResponse.of(userDocs, count);
    }

    public UserDoc update(UpdateUserRequest request) throws UserNotExistException {
        Optional<UserDoc> userDocOptional = userRepository.findById(request.getId());
        if (userDocOptional.isEmpty()) throw new UserNotExistException();
        UserDoc userDoc = userDocOptional.get();
        userDoc.setLastName(request.getLastName());
        userDoc.setFirstName(request.getFirstName());
        userDoc.setAddress(request.getAddress());
        userDoc.setCompany(request.getCompany());
        userRepository.save(userDoc);
        return userDoc;
    }
    public void delete(ObjectId id){
        userRepository.deleteById(id);
    }
}
