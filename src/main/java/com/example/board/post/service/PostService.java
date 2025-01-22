package com.example.board.post.service;

import com.example.board.author.domain.Author;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.domain.Post;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.dtos.PostUpdatedReq;
import com.example.board.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<PostListRes> findAllPaging(Pageable pageable){
        Page<Post> pagePosts = postRepository.findAll(pageable);
        return pagePosts.map(p->p.toListFromEntity());
    }

    public PostDetailRes findById(Long id){
       return postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("the post does not exist")).toDetailDtoFromEntity();
    }

    public void update(Long id, PostUpdatedReq postUpdatedReq){
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("the post does not exist"));
        post.PostUpdate(postUpdatedReq);
        postRepository.save(post);
    }

    public void deleteById(Long id){
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("the post does not exist"));
        postRepository.delete(post);
    }
}
