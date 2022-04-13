package com.example.baegether.api;


import com.example.baegether.dto.PostDto;
import com.example.baegether.dto.UserDto;
import com.example.baegether.service.PostService;
import com.example.baegether.service.search.PostSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/board")
public class PostApiController {

    private final PostService postService;

    /**
     * 게시물 작성
     * **/
    @PostMapping("")
    public ResponseEntity<PostDto> create(@RequestBody PostDto dto) {
        PostDto resultDto = postService.create(dto);
        return ResponseEntity.status(HttpStatus.OK).body(resultDto);
    }

    /**
     *  게시물 조회
     */
    @GetMapping("")
    public ResponseEntity<List<PostDto>> index(@ModelAttribute("postSearch") PostSearch postSearch) {
        List<PostDto> resultDtos = postService.index(postSearch);
        return ResponseEntity.status(HttpStatus.OK).body(resultDtos);
    }

    /**
     *  게시물 수정
     */
    @PatchMapping("/{boardId}")
    public ResponseEntity<PostDto> update(@PathVariable Long boardId,
                                          @RequestBody PostDto dto) {
        PostDto updated = postService.update(boardId, dto);

        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    /**
     *  게시물 삭제
     */
    @DeleteMapping("/{boardId}")
    public ResponseEntity<PostDto> delete(@PathVariable Long boardId) {
        PostDto deleted = postService.delete(boardId);

        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.OK).body(deleted) :
                    ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 게시물 (방) 참여
     */
    @PostMapping("/{boardId}/join")
    public ResponseEntity<PostDto> joinRoom(@PathVariable Long boardId) {
        PostDto postDto = postService.joinRoom(boardId);

        return (postDto != null) ?
                ResponseEntity.status(HttpStatus.OK).body(postDto) :
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    /**
     * 게시물 (방) 나가기
     */
    @DeleteMapping("/{boardId}/exit")
    public ResponseEntity<PostDto> exitRoom(@PathVariable Long boardId) {
        PostDto postDto = postService.exitRoom(boardId);

        return (postDto != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(postDto) :
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
