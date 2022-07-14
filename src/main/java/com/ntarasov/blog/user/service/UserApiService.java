package com.ntarasov.blog.user.service;


import com.ntarasov.blog.user.api.request.RegistrationRequest;
import com.ntarasov.blog.user.exception.UserExistException;
import com.ntarasov.blog.user.model.UserDoc;
import com.ntarasov.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
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
        userDoc.setPassword(DigestUtils.md5DigestAsHex(request.getPassword().getBytes()));
        userDoc = userRepository.save(userDoc);
        return userDoc;
    }

    public Optional<UserDoc> findById(ObjectId id){
        return userRepository.findById(id);
    }
}
