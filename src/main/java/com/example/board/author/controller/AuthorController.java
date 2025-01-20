package com.example.board.author.controller;

import com.example.board.author.dto.AuthorListRes;
import com.example.board.author.dto.AuthorSaveReq;
import com.example.board.author.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final  AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping("/create")
    public String save(@Valid AuthorSaveReq authorSaveReq){
        authorService.save(authorSaveReq);
        return "ok";
    }

    @GetMapping("/list")
    public List<AuthorListRes> findAll(){
        return authorService.findAll();
    }


}
