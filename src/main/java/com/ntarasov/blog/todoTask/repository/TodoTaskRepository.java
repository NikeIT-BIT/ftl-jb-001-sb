package com.ntarasov.blog.todoTask.repository;

import com.ntarasov.blog.todoTask.model.TodoTaskDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TodoTaskRepository extends MongoRepository<TodoTaskDoc, ObjectId> {

}
