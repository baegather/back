package com.example.baegether.api;


import com.example.baegether.domain.Post;
import com.example.baegether.dto.PostCreateDto;
import com.example.baegether.service.PostService;
import com.example.baegether.service.search.PostSearch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/board")
public class PostApiController {

    private final PostService postService;

    /**
     * 게시물 작성
     * **/
    @PostMapping("/create")
    public ResponseEntity<PostCreateDto> create(@RequestBody PostCreateDto dto) {
        PostCreateDto resultDto = postService.create(dto);
        return ResponseEntity.status(HttpStatus.OK).body(resultDto);
    }

    /**
     *  게시물 조회
     */
    // 전체 조회
    @GetMapping("")
    public ResponseEntity<List<PostCreateDto>> index(@ModelAttribute("postSearch") PostSearch postSearch) {
        List<PostCreateDto> resultDtos = postService.index(postSearch);
        return ResponseEntity.status(HttpStatus.OK).body(resultDtos);
    }

}
