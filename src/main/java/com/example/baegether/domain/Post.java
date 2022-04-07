package com.example.baegether.domain;

import com.example.baegether.domain.enums.Location;
import com.example.baegether.domain.enums.MenuCategory;
import com.example.baegether.dto.PostDto;
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

    @Embedded
    private TimeStamp timeStamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Room> room = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    public void patch(PostDto dto) {
        if (this.id != dto.getId())
            throw new IllegalArgumentException("게시물 수정 실패! 잘못된 id 입력");

        if(dto.getTitle() != null) this.title = dto.getTitle();
        if(dto.getContents() != null) this.contents = dto.getContents();
        if(dto.getLocation() != null) this.location = dto.getLocation();
        if(dto.getHopeOrderTime() != null) this.hopeOrderTime = dto.getHopeOrderTime();
        if(dto.getMenuCategory() != null) this.menuCategory = dto.getMenuCategory();
        this.peopleMax = dto.getPeopleMax();
        this.timeStamp.setCreatedTime(LocalDateTime.now());

    }

    public static Post createPost(PostDto dto, User user) {

        return new Post(
                dto.getId(),
                dto.getTitle(),
                dto.getContents(),
                dto.getMenuCategory(),
                dto.getPeopleMax(),
                dto.getLocation(),
                dto.getHopeOrderTime(),
                dto.getTimeStamp(),
                user
        );

    }

    public void joinRoom(Room room) {
        this.room.add(room);
    }

    public Boolean isJoinRoom() {
        if (room.size() >= peopleMax) {
            return false;
        }
        return true;
    }

    public Post(Long id, String title, String contents, MenuCategory menuCategory, int peopleMax, Location location, LocalDateTime hopeOrderTime, TimeStamp timeStamp, User user) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.menuCategory = menuCategory;
        this.peopleMax = peopleMax;
        this.location = location;
        this.hopeOrderTime = hopeOrderTime;
        this.timeStamp = timeStamp;
        this.user = user;
    }
}
