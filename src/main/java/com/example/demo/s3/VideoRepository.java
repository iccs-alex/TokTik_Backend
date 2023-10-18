package com.example.demo.s3;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.s3.VideoDetails;
import org.springframework.data.mongodb.repository.Query;

public interface VideoRepository extends MongoRepository<VideoDetails, String> {
  List<VideoDetails> findByKey(String key);

  @Query(value="{'key': ?0}", delete=true)
  VideoDetails deleteByKey(String key);
}
