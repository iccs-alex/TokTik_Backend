package com.example.demo.server;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.server.VideoDetails;

public interface VideoRepository extends MongoRepository<VideoDetails, String> {
  List<VideoDetails> findByKey(String key);

  @Query(value="{'key': ?0}", delete=true)
  VideoDetails deleteByKey(String key);

}
