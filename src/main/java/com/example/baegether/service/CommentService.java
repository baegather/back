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
        //Board 정보 조회
        Post post = postRepository.findById(commentDto.getBoardId())
                .orElseThrow(()->new NoSuchDataException("게시물을 찾을 수 없습니다."));

        //User 정보 조회
        User user = userService.getUserFromOAuth2(); //throwable NoSuchDataException

        //Board와 User가 모두 조회되면 댓글이 저장됨.
        Comment comment = new Comment(commentDto.getContent(),
                new TimeStamp(LocalDateTime.now(), LocalDateTime.now()), post, user);

        commentRepository.save(comment);
    }

    //게시물의 댓글들 조회
    @Transactional(readOnly = true)
    public List<CommentDto> findCommentsByBoardId(Long boardId) throws NoSuchDataException{
        //board 조회
        Post post= postRepository.findById(boardId)
                .orElseThrow(()-> new NoSuchDataException("게시물을 찾을 수 없습니다."));

        //board의 comment 리스트 조회
        List<Comment> commentList = post.getCommentList();
        ArrayList<CommentDto> result = new ArrayList<>();

        //comment들을 Response에 담기 위해 DTO로 변환
        commentList.forEach(x->result.add(new CommentDto(boardId, x.getContents(), x.getUser().getNickname())));
        return result;
    }

    //댓글 삭제
    public void deleteComment(Long commentId)throws NoSuchDataException, UnauthorizedUserException{

        //유저 정보 조회
        User user = userService.getUserFromOAuth2(); //throwable NoSuchDataException

        //댓글 정보 조회
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new NoSuchDataException("댓글을 찾을 수 없습니다."));

        //댓글 작성자와 요청하는 유저가 다른지 확인
        if(!user.equals(comment.getUser())){
            throw new UnauthorizedUserException("작성자와 다른 유저입니다.");
        }

        //유저가 존재하고, 요청한 유저가 댓글 작성자와 일치하고, 댓글이 존재한다면 로직 수행.
        commentRepository.delete(comment);
    }

    public void updateComment(Long commentId, CommentDto commentDto) throws NoSuchDataException, UnauthorizedUserException{
        //유저 정보 조회
        User user = userService.getUserFromOAuth2(); //throwable NoSuchDataException

        //댓글 정보 조회
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new NoSuchDataException("댓글을 찾을 수 없습니다."));

        //댓글 작성자와 요청하는 유저가 다른지 확인
        if(!user.equals(comment.getUser())){
            throw new UnauthorizedUserException("작성자와 다른 유저입니다.");
        }

        //유저가 존재하고, 요청한 유저가 댓글 작성자와 일치하고, 댓글이 존재한다면 로직 수행.
        comment.setContents(comment.getContents());
    }
}
