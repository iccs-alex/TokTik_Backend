package com.example.demo.videos;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Document(collection = "videos")
public class VideoDetails {

    @Id
    public String key;
    public String title;
    public String description;
    
    public Integer viewCount;
    public Integer likeCount;

    public List<String> userLikes;

    public VideoDetails(String key, String title, String description) {
        this.key = key;
        this.title = title;
        this.description = description;
        this.viewCount = 0;
        this.likeCount = 0;
        this.userLikes = new ArrayList<String>();
    }

}
