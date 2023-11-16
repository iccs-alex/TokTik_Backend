package com.example.demo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "subbed_videos")
public class SubbedVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String username;

    public String videoKey;

}
