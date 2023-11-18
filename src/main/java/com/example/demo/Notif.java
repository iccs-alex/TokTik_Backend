package com.example.demo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "user_notifs")
@NoArgsConstructor
public class Notif {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String message;
    public String videoKey;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "username")
    private MyUser user;

    public Notif(String message, String videoKey, MyUser user) {
        this.message = message;
        this.videoKey = videoKey;
        this.user = user;
    }
 
    public void removeUser() {
        this.user = null;
    }

}
