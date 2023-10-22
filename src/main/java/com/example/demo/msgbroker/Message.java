package com.example.demo.msgbroker;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    
    private String videoKey;
    
    @Override
    public String toString() {
        return this.videoKey;
    }
}
