package com.example.baegether.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {


    public User(Long id, String email, String nickname, String thumbnail, TimeStamp timeStamp) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.thumbnail = thumbnail;
        this.timeStamp = timeStamp;
    }

    @Id @Column(name = "user_id") @NotNull
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String nickname;
    @NotNull
    private String thumbnail;

    @Enumerated
    private TimeStamp timeStamp;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();



}
