package com.example.baegether.service;

import com.example.baegether.domain.Post;
import com.example.baegether.domain.Room;
import com.example.baegether.domain.User;
import com.example.baegether.dto.PostDto;
import com.example.baegether.dto.UserDto;
import com.example.baegether.exceptions.NoSuchDataException;
import com.example.baegether.exceptions.UnauthorizedUserException;
import com.example.baegether.repository.custom.post.PostRepository;
import com.example.baegether.repository.UserRepository;
import com.example.baegether.repository.custom.room.RoomRepository;
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
    private final RoomRepository roomRepository;
    private final UserService userService;
    /**
     * 조회
     * **/
    public List<PostDto> index(PostSearch postSearch) {
        List<Post> results = postRepository.findFiltered(postSearch);
        return results.stream()
                .map(r -> PostDto.of(r))
                .collect(Collectors.toList());
    }


    /**
     *  게시물 작성
     */
    @Transactional
    public PostDto create(PostDto dto) {
        // 게시물 작성자 확인
        User user = userService.getUserFromOAuth2();
        User findUser = userRepository.findById(user.getId())
                        .orElseThrow(() -> new UnauthorizedUserException("게시물 작성 실패! 해당 유저가 없습니다."));

        // create post entity
        Post post = Post.createPost(dto, findUser);

        // create room entity and insert into post
        Room room = new Room();
        room.patch(post, dto.getUserId());
        post.joinRoom(room);


        // save post entity in DB
        Post created = postRepository.save(post);

        return PostDto.of(created);
    }

    /**
     * 게시물 수정
     */
    @Transactional
    public PostDto update(Long postId,PostDto dto) {
        // 게시물 찾기
        User user = userService.getUserFromOAuth2();
        Post target = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchDataException("게시물이 존재하지 않음"));

        // 게시물 소유 확인
        if(target.getUser().getId() != user.getId()) {
            return null;
        }

        // target update
        target.patch(dto);

        return PostDto.of(target);
    }

    /**
     * 게시물 삭제
     */
    @Transactional
    public PostDto delete(Long postId) {
        // 게시물 존재여부 확인
        User user = userService.getUserFromOAuth2();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchDataException("존재하지않는 게시물 입니다"));

        // 게시물 작성한 유저 확인
        if (post.getUser().getId() != user.getId()) {
            throw new UnauthorizedUserException("잘못된 유저");
        }

        // 삭제
        postRepository.delete(post);
        return PostDto.of(post);

    }


    /**
     * 게시물 입장
     * */
    @Transactional
    public PostDto joinRoom(Long postId) {
        User user = userService.getUserFromOAuth2();
        // 게시물이 존재 체크
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchDataException("게시물이 존재하지 않음"));

        // user의 게시물 존재여부 확인 (존재하면 입장)
        if (isUserInRoom(user.getId(), post)) {
            return PostDto.of(post);
        }

        // 게시물(방) 인원 체크
        if (post.getRoom().size()>=post.getPeopleMax()) {
            throw new NoSuchDataException("방 인원수 초과");
        }

        // create room
        Room room = new Room();
        room.patch(post, user.getId());
        roomRepository.save(room);

        return PostDto.of(post);
    }


    /**
     * 게시물 나가기
     * */
    @Transactional
    public PostDto exitRoom(Long postId) {
        User user = userService.getUserFromOAuth2();
        // 게시물이 존재 체크
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchDataException("게시물이 존재하지 않음"));

        // 게시물이 자신의 소유인 경우
        if (post.getUser().getId() == user.getId()) {
            throw new NoSuchDataException("방의 소유자는 나갈 수 없음");
        }

        // user의 게시물 존재여부 확인
        if (!isUserInRoom(user.getId(), post)) {
            throw new UnauthorizedUserException("게시물(방)에 존재하지 않음");
        }

        Room room = roomRepository.findRoom(post.getId(), user.getId());

        // 삭제
        roomRepository.delete(room);

        return PostDto.of(post);
    }




    private boolean isUserInRoom(Long userId, Post post) {
        for(Room r : post.getRoom()) {
            if (r.getUserId() == userId) {
                return true;
            }
        }
        return false;
    }
}
