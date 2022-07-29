package com.ntarasov.blog.album.repository;

import com.ntarasov.blog.album.model.AlbumDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AlbumRepository extends MongoRepository<AlbumDoc, ObjectId> {

}
