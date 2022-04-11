package com.example.baegether.dto;


import com.example.baegether.domain.Post;
import com.example.baegether.domain.TimeStamp;
import com.example.baegether.domain.enums.Location;
import com.example.baegether.domain.enums.MenuCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Data
public class PostDto {

    private Long id;
    private String title;
    private String contents;
    private MenuCategory menuCategory;
    private int peopleMax;
    private Location location;
    private LocalDateTime hopeOrderTime;
    private TimeStamp timeStamp;

    @JsonProperty("user_id")
    private Long userId;


    public static PostDto of(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContents(),
                post.getMenuCategory(),
                post.getPeopleMax(),
                post.getLocation(),
                post.getHopeOrderTime(),
                post.getTimeStamp(),
                post.getUser().getId()
        );
    }

}
