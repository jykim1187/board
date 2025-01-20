package com.example.board.post.controller;

import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/create")
    public String create(@Valid PostSaveReq postSaveReq){
        postService.save(postSaveReq);
        return "okay";
    }

    @GetMapping("/list")
    public List<PostListRes> list(){
         return postService.findAll();
    }

}
