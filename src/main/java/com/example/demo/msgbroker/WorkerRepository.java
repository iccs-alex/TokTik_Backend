package com.example.demo.msgbroker;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.s3.VideoDetails;
import org.springframework.data.mongodb.repository.Query;

public interface WorkerRepository extends MongoRepository<WorkerStatus, String> {
  String findById(Integer id);

  @Query(value="{'status': ?0}", delete=true)
  VideoDetails deleteById(Integer id);
}
