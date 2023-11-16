package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {

    MyUser findFirstByUsername(String username);

    @Query("select distinct u from MyUser u where ?1 member of u.subbedVideos")
    List<MyUser> findAllUsersBySubbedVideo(String videoKey);
}
