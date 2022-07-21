package com.ntarasov.blog.article.repository;

import com.ntarasov.blog.article.model.ArticleDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ArticleRepository extends MongoRepository<ArticleDoc, ObjectId> {

}
