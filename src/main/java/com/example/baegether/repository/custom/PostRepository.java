package com.example.baegether.repository.custom;

import com.example.baegether.domain.Post;
import com.example.baegether.repository.custom.PostRepositoryCustom;
import com.example.baegether.service.search.PostSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom{

}
