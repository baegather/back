package com.example.baegether.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String contents;

    public void setContents(String contents) {
        this.contents = contents;
        this.timeStamp.setUpdatedTime(LocalDateTime.now());
    }

    @Enumerated
    private TimeStamp timeStamp;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;


    public Comment(String contents, TimeStamp timeStamp, Post post, User user) {
        this.contents = contents;
        this.timeStamp = timeStamp;
        this.post = post;
        this.user = user;
    }
}
