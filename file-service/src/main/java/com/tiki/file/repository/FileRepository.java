package com.tiki.file.repository;

import com.tiki.file.entity.Files;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<Files, String> {
}
