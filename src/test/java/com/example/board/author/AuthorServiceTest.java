package com.example.board.author;

import com.example.board.author.domain.Author;
import com.example.board.author.domain.Role;
import com.example.board.author.dto.AuthorSaveReq;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;


    @Test
    public void authorSaveTest(){
        AuthorSaveReq authorSaveReq = new AuthorSaveReq("testkim","testkim@naver.com","1234", Role.ADMIN);
        authorService.save(authorSaveReq);
        Author author = authorRepository.findByEmail("testkim@naver.com").orElse(null);
        Assertions.assertEquals(authorSaveReq.getEmail(),author.getEmail());
    }


    @Test
    public void findAllTest(){
       int beforSize = authorRepository.findAll().size();
        authorRepository.save(Author.builder().email("aaaa@daum.net").name("lee").password("1234").build());
        authorRepository.save(Author.builder().email("aaaa1@daum.net").name("lo").password("1234").build());
        authorRepository.save(Author.builder().email("aaaa2@daum.net").name("kee").password("1234").build());

        int afterSize = authorService.findAll().size();
        Assertions.assertEquals(beforSize+3,afterSize);
    }
}
