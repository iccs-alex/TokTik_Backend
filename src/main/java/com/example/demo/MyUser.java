package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class MyUser {

    @Id
    @Column(unique = true)
    private String username;

    private String password;

    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notif> notifs = new ArrayList<>();

    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "user_subbedVideos", joinColumns = @JoinColumn(name = "username"))
    @Column(name = "subbedVideo", nullable = false)
    private List<String> subbedVideos = new ArrayList<>();

    public void addNotif(Notif notif) {
        notifs.add(0, notif);
    }

    public void removeNotif(Long notifId) {
        notifs.removeIf(n -> syncDeletion(n, notifId));
    }
    
    private boolean syncDeletion(Notif notif, Long notifId) {
        if(notif.getId().equals(notifId)) {
            notif.removeUser();
            return true;
        }
        return false;
    }

    public MyUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
