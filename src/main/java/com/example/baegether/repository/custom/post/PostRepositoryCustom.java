package com.example.baegether.repository.custom.post;


import com.example.baegether.domain.Post;
import com.example.baegether.service.search.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> findFiltered(PostSearch postSearch);

}