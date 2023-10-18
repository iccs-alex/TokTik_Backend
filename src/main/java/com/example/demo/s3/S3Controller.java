package com.example.demo.s3;

import com.example.demo.s3.VideoDetails;
import com.example.demo.s3.VideoRepository;
import com.example.demo.User;
import com.example.demo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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

import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@RestController
public class S3Controller {

    @Autowired
    VideoRepository videoRepository;

    String bucketName = "toktik-videos";
    Regions region = Regions.AP_SOUTHEAST_1;

    @GetMapping("/api/video")
    public String getVideo(@RequestParam String key) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();

        URL url = s3.generatePresignedUrl(bucketName, key, new Date(new Date().getTime() + 100000), HttpMethod.GET);
        
        return url.toString();
    }

    @PutMapping("/api/video")
    public String putVideo(@RequestBody VideoDetails videoDetails) {
        try {
            VideoDetails _videoDetails = videoRepository.save(new VideoDetails(videoDetails.getKey(), videoDetails.getTitle(), videoDetails.getDescription()));
            for (VideoDetails video: videoRepository.findAll()) {
                System.out.println(video.getTitle());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
        URL url = s3.generatePresignedUrl(bucketName, videoDetails.getKey(), new Date(new Date().getTime() + 100000), HttpMethod.PUT);
        
        return url.toString();
    }

    @DeleteMapping("/api/video")
    public String deleteVideo(@RequestParam String key) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();

        URL url = s3.generatePresignedUrl(bucketName, key, new Date(new Date().getTime() + 100000), HttpMethod.DELETE);

        return url.toString();
    }
    
    @GetMapping("/api/videos")
    public List<VideoDetails> getAllVideos() {
        System.out.println("HELLOTHERE");
        return videoRepository.findAll();
    }

    @GetMapping("/api/vids")
    public List<VideoDetails> getAllVids() {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
        List<VideoDetails> videosDetails = new ArrayList<VideoDetails>();
        ListObjectsV2Result objectList = s3.listObjectsV2(bucketName);

        List<S3ObjectSummary> objects = objectList.getObjectSummaries();
        for (S3ObjectSummary os : objects) {
            System.out.println("* " + os.getKey());
            videosDetails.add(VideoDetails.builder().title(os.getKey()).build());
        }

        return videosDetails;
    }



}
