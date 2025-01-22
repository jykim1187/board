package com.example.board.post.controller;

import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.dtos.PostUpdatedReq;
import com.example.board.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
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
    public String list(Model model){

        model.addAttribute(postService.findAll());
        return "author_list";
    }

    @GetMapping("/list/paging")
//    페이징처리를 위한 데이터 형식 : localhost:8080/post/list/paging?size=10&page=0&sort=createdTime,desc
    public Page<PostListRes> listPaging(@PageableDefault(size = 10, sort= "createdTime", direction = Sort.Direction.DESC)Pageable pageable){
// 들어오는 데이터형식에 size와 sort에 관해 입력하지 않아도 여기 default어노테이션을 걸어놓아서 괜찮다.
        return postService.findAllPaging(pageable);

    }

    @GetMapping("/list/{id}")
    public PostDetailRes findById(@PathVariable Long id){
           return  postService.findById(id);
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, PostUpdatedReq postUpdatedReq){
         postService.update(id,postUpdatedReq);
         return "okay";
    }
    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id){
        postService.deleteById(id);
        return "okay";
    }
}
