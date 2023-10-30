package com.example.demo.msgbroker;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Document(collection="workerStatus")
public class WorkerStatus {
 
    public Integer id;
    public String statusMessage;

    public WorkerStatus(Integer id, String statusMessage) {
        this.id = id;
        this.statusMessage = statusMessage;
    }

}
