package com.example.baegether.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @NotNull
    @Column(name = "room_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @NotNull
    @JoinColumn(name = "post_id")
    private Post post;

    @NotNull
    private Long userId;

    public void patch(Post post, Long userId) {
        if (post != null) this.post = post;
        if (userId != null) this.userId = userId;
    }



}
