package com.example.demo.msgbroker;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.videos.VideoDetails;

public interface WorkerRepository extends MongoRepository<WorkerStatus, String> {
  String findById(Integer id);

  @Query(value="{'status': ?0}", delete=true)
  VideoDetails deleteById(Integer id);
}
