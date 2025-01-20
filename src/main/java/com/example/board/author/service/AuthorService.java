package com.example.board.author.service;

import com.example.board.author.domain.Author;
import com.example.board.author.dto.AuthorListRes;
import com.example.board.author.dto.AuthorSaveReq;
import com.example.board.author.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }


    public void save(AuthorSaveReq authorSaveReq){

        if(authorRepository.findByEmail(authorSaveReq.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }
        Author author =  authorSaveReq.toEntity();
        authorRepository.save(author);

    }

    public List<AuthorListRes> findAll(){
        return authorRepository.findAll().stream().map(a->a.listDtoFromEntity()).collect(Collectors.toList());
    }
}
