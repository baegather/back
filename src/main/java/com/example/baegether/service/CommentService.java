package com.example.baegether.service;

import com.example.baegether.domain.Comment;
import com.example.baegether.domain.TimeStamp;
import com.example.baegether.domain.User;
import com.example.baegether.dto.CommentDto;
import com.example.baegether.exceptions.NoSuchDataException;
import com.example.baegether.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    public Long save(CommentDto commentDto) throws NoSuchDataException {
        //후에 post를 조회 후 가져 오는게 필요
        //Post post = postService.findById(commentDto.getBoardId()) //NoSuchData throwable
        User user = userService.getUserFromOAuth2();
        Comment comment = new Comment(0L, commentDto.getContent(),
                new TimeStamp(LocalDateTime.now(), LocalDateTime.now()), null, user);

        return comment.getId();
    }

    public
}
