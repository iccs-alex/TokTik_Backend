package com.example.demo.s3;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Document(collection="videos")
public class VideoDetails {
 
    public String key;
    public String title;
    public String description;

    public VideoDetails(String key, String title, String description) {
        this.key = key;
        this.title = title;
        this.description = description;
    }

}
