package com.ntarasov.blog.file.repository;

import com.ntarasov.blog.file.model.FileDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface FileRepository extends MongoRepository<FileDoc, ObjectId> {

}
