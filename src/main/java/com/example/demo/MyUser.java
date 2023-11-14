package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tbl_user")
public class MyUser {

    @Id
    @Column(unique = true)
    private String username;

    private String password;

    private String role;

}
