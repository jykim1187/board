package com.example.board.author.controller;

//import com.example.board.author.dto.AuthorDetailDto;
import com.example.board.author.dto.AuthorDetailRes;
import com.example.board.author.dto.AuthorListRes;
import com.example.board.author.dto.AuthorSaveReq;
import com.example.board.author.dto.AuthorUpdateReq;
import com.example.board.author.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {

    private final  AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping("/create")
    public String register(){
        return "/author/author_create";
    }

    @PostMapping("/create")
    public String save(@Valid AuthorSaveReq authorSaveReq){
        authorService.save(authorSaveReq);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String authorLoginScreen(){
        return "/author/author_login";
    }

    @GetMapping("/list")
    public String findAll(Model model){
        List<AuthorListRes>  authorListResList =  authorService.findAll();
        model.addAttribute("authorList",authorListResList);
        return "/author/author_list";
    }

//    원래 deleteMapping이 맞으나 우리는 나중에 화면을 같이 개발할 때 편하게 하기 위해 get으로(왜냐하면 js가 아닌 순수html에는 get,post둘 밖에 없기 때문)
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        authorService.deleteById(id);
        return "ok";
    }

    @GetMapping("/detail/{id}")
    public String findById(@PathVariable Long id, Model model){
       model.addAttribute("author",authorService.fingById(id));
       return "/author/author_detail";
    }

    @PostMapping("update/{id}")//원래라면 PatchMapping이 맞으나 나중에 화면개발 편하게 하기 위해 임시적으로
    public String update(@PathVariable Long id, @ModelAttribute AuthorUpdateReq authorUpdateReq){
        authorService.update(id, authorUpdateReq);
        return "okay";
    }

}
