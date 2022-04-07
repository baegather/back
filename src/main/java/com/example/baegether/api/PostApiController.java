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
    @PostMapping("/create")
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
     * 게시물 (방) 참여
     */
    @PostMapping("/{board_id}/join")
    public ResponseEntity<PostDto> joinRoom(@PathVariable Long board_id, @RequestBody UserDto userDto) {
        PostDto postDto = postService.joinRoom(board_id, userDto.getId());

        return ResponseEntity.status(HttpStatus.OK).body(postDto);
    }


}
