package com.ntarasov.blog.comment.repository;

import com.ntarasov.blog.comment.model.CommentDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CommentRepository extends MongoRepository<CommentDoc, ObjectId> {

}
