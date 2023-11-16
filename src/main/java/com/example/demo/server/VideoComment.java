package com.example.demo.server;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Document(collection = "video_comments")
public class VideoComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long commentKey;
    
    public String username;
    public String comment;

    public VideoComment(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

}
