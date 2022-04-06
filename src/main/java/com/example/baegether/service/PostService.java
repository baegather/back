package com.example.baegether.service;

import com.example.baegether.domain.Post;
import com.example.baegether.domain.Room;
import com.example.baegether.domain.User;
import com.example.baegether.dto.PostCreateDto;
import com.example.baegether.repository.custom.PostRepository;
import com.example.baegether.repository.UserRepository;
import com.example.baegether.service.search.PostSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 조회
     * **/
    public List<PostCreateDto> index(PostSearch postSearch) {
        List<Post> results = postRepository.findFiltered(postSearch);
        return results.stream()
                .map(r -> PostCreateDto.of(r))
                .collect(Collectors.toList());
    }




    /**
     *  게시물 작성
     */
    @Transactional
    public PostCreateDto create(PostCreateDto dto) {
        // 게시물 작성자 확인
        User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new IllegalArgumentException("게시물 작성 실패! 해당 유저가 없습니다."));

        // create post entity
        Post post = Post.createPost(dto, user);

        // create room entity and insert into post
        Room room = new Room();
        room.patch(post, dto.getUserId());
        post.joinRoom(room);


        // save post entity in DB
        Post created = postRepository.save(post);

        return PostCreateDto.of(created);
    }




}
