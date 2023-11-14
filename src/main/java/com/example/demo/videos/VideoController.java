package com.example.demo.videos;

import com.example.demo.msgbroker.MessagePublisher;
import com.example.demo.videos.VideoDetails;
import com.example.demo.videos.VideoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.HttpMethod;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.json.simple.JSONObject;

@RestController
public class VideoController {

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    String socketioChannel = "socketio";

    String s3BucketName = "toktik-videos";
    Regions region = Regions.AP_SOUTHEAST_1;

    @GetMapping("/api/video")
    public String getVideo(@RequestParam String key) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();

        URL url = s3.generatePresignedUrl(s3BucketName, key, new Date(new Date().getTime() + 100000), HttpMethod.GET);

        return url.toString();
    }

    @PutMapping("/api/video")
    public String putVideo(@RequestBody VideoDetails videoDetails) {
        try {
            VideoDetails _videoDetails = videoRepository.save(
                    new VideoDetails(videoDetails.getKey(), videoDetails.getTitle(), videoDetails.getDescription()));
        } catch (Exception e) {
            System.out.println(e);
        }
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
        URL url = s3.generatePresignedUrl(s3BucketName, "videos/" + videoDetails.getKey(),
                new Date(new Date().getTime() + 100000), HttpMethod.PUT);

        return url.toString();
    }

    @DeleteMapping("/api/video")
    public String deleteVideo(@RequestParam String key) {
        try {
            VideoDetails video_ = videoRepository.deleteByKey(key);
        } catch (Exception e) {
            System.out.println(e);
        }

        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();

        URL url = s3.generatePresignedUrl(s3BucketName, key, new Date(new Date().getTime() + 100000),
                HttpMethod.DELETE);

        return url.toString();
    }

    @GetMapping("/api/videos")
    public List<VideoDetails> getAllVideos() {
        return videoRepository.findAll();
    }

    @GetMapping("/api/video/details")
    public VideoDetails getVideoDetails(@RequestParam String key) {
        return videoRepository.findByKey(key).get(0);
    }

    @PostMapping("/api/video/view")
    public Integer viewVideo(@RequestParam String key) {
        VideoDetails videoDetails_ = videoRepository.findByKey(key).get(0);
        Integer newViewCount = videoDetails_.getViewCount() + 1;
        videoDetails_.setViewCount(newViewCount);
        videoRepository.save(videoDetails_);

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("action", "viewUpdate");
        jsonObj.put("room", "video:" + key);
        jsonObj.put("viewCount", newViewCount);
        sendDataToChannel(socketioChannel, jsonObj);

        return newViewCount;
    }

    @PostMapping("/api/video/like")
    public Integer likeVideo(@RequestParam String key, @RequestParam String username) {

        // Update Like Count
        VideoDetails videoDetails_ = videoRepository.findByKey(key).get(0);
        Integer newLikeCount = videoDetails_.getLikeCount() + 1;
        videoDetails_.setLikeCount(newLikeCount);
        videoDetails_.getUserLikes().add(username);
        videoRepository.save(videoDetails_);
        System.out.println(videoRepository.findByKey(key).get(0).getUserLikes());

        // Send socketio message
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("action", "likeUpdate");
        jsonObj.put("room", "video:" + key);
        jsonObj.put("likeCount", newLikeCount);
        sendDataToChannel(socketioChannel, jsonObj);

        return newLikeCount;
    }

    @PostMapping("/api/video/unlike")
    public Integer unlikeVideo(@RequestParam String key, @RequestParam String username) {
        VideoDetails videoDetails_ = videoRepository.findByKey(key).get(0);
        Integer newLikeCount = videoDetails_.getLikeCount() - 1;
        videoDetails_.setLikeCount(newLikeCount);
        videoDetails_.getUserLikes().remove(username);
        videoRepository.save(videoDetails_);
        System.out.println(videoRepository.findByKey(key).get(0).getUserLikes());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("action", "likeUpdate");
        jsonObj.put("room", "video:" + key);
        jsonObj.put("likeCount", newLikeCount);
        sendDataToChannel(socketioChannel, jsonObj);

        return newLikeCount;
    }

    public void sendDataToChannel(String channel, JSONObject data) {
        redisTemplate.convertAndSend(channel, data);
    }

}
