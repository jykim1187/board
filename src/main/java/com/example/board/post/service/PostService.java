package com.example.board.post.service;

import com.example.board.author.domain.Author;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.domain.Post;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    public PostService(PostRepository postRepository,AuthorRepository authorRepository){
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }


    public void save(PostSaveReq postSaveReq){
        Author author = authorRepository.findByEmail(postSaveReq.getEmail()).orElseThrow(()->new EntityNotFoundException("no id"));
        postRepository.save(postSaveReq.toEntity(author));
    }


    public List<PostListRes> findAll(){
        return postRepository.findAll().stream().map(p->p.toListFromEntity()).collect(Collectors.toList());
    }
}
