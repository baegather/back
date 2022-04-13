package com.example.baegether.service.search;

import com.example.baegether.domain.enums.Location;
import com.example.baegether.domain.enums.MenuCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearch {

    private String keyword;
    private MenuCategory menuCategory;
    private Location location;
    private int page;
    private int perPage;

}
