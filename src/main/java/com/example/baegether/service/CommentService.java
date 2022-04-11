package com.example.baegether.service;

import com.example.baegether.domain.Comment;
import com.example.baegether.domain.Post;
import com.example.baegether.domain.TimeStamp;
import com.example.baegether.domain.User;
import com.example.baegether.dto.CommentDto;
import com.example.baegether.exceptions.NoSuchDataException;
import com.example.baegether.exceptions.UnauthorizedUserException;
import com.example.baegether.repository.CommentRepository;
import com.example.baegether.repository.custom.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostRepository postRepository;

    //댓글 저장
    public void save(CommentDto commentDto) throws NoSuchDataException {
        Post post = postRepository.findById(commentDto.getBoardId())
                .orElseThrow(()->new NoSuchDataException("게시물을 찾을 수 없습니다."));

        User user = userService.getUserFromOAuth2(); //throwable NoSuchDataException
        Comment comment = new Comment(0L, commentDto.getContent(),
                new TimeStamp(LocalDateTime.now(), LocalDateTime.now()), post, user);

    }

    //게시물의 댓글들 조회
    @Transactional(readOnly = true)
    public List<CommentDto> findCommentsByBoardId(Long boardId) throws NoSuchDataException{
        Post post;
        try {
            post = postRepository.getById(boardId);
        }catch(EntityNotFoundException e){
            throw new NoSuchDataException("게시물을 찾을 수 없습니다.");
        }

        List<Comment> commentList = post.getCommentList();
        ArrayList<CommentDto> result = new ArrayList<>();

        commentList.stream().forEach(x->result.add(new CommentDto(boardId, x.getContents(), x.getUser().getNickname())));
        return result;
    }

    //댓글 삭제
    public void deleteComment(Long commentId)throws NoSuchDataException, UnauthorizedUserException{

        User user = userService.getUserFromOAuth2(); //throwable NoSuchDataException
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new NoSuchDataException("댓글을 찾을 수 없습니다."));

        //댓글 작성자와 요청하는 유저가 다른지 확인
        if(!user.equals(comment.getUser())){
            throw new UnauthorizedUserException("작성자와 다른 유저입니다.");
        }
        commentRepository.delete(comment);
    }

    public void updateComment(Long commentId, CommentDto commentDto) throws NoSuchDataException, UnauthorizedUserException{
        User user = userService.getUserFromOAuth2(); //throwable NoSuchDataException
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new NoSuchDataException("댓글을 찾을 수 없습니다."));

        //댓글 작성자와 요청하는 유저가 다른지 확인
        if(!user.equals(comment.getUser())){
            throw new UnauthorizedUserException("작성자와 다른 유저입니다.");
        }
        comment.setContents(comment.getContents());
    }
}
