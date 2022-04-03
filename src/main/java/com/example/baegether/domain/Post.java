package com.example.baegether.domain;

import com.example.baegether.domain.enums.Location;
import com.example.baegether.domain.enums.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    private String title;
    private String contents;

    @Enumerated(EnumType.STRING)
    private MenuCategory menuCategory; //[한식, 분식, 중식, 일식, 양식, 카페, 야식]
    private int peopleMax;
    @Enumerated(EnumType.STRING)

    private Location location; //[오름관, 푸름관, 디지털관, 글로벌관, 테크노관, 학생회관, 아름관, 옥계중학교앞, 옥계BHC앞, 블랙홀상가앞]
    private LocalDateTime hopeOrderTime;

    @Enumerated
    private TimeStamp timeStamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Room> room = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

}
