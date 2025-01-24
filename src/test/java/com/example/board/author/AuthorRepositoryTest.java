package com.example.board.author;


import com.example.board.author.domain.Author;
import com.example.board.author.domain.Role;
import com.example.board.author.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional // 테스트 완료 후 데이터가 실제 DB에 insert되지 않고, 롤백시키기 위한 목적 //저 2개가 붙어서 테스트환경에서는 성공해도 롤백
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

   @Test //테스트어노테이션을 붙이면 옆에 실행버튼이 생긴다
    public void authorSaveTest(){
//        테스트검증로직 : 객체생성 ->save-> 재조회 -> 객체와 조회한 객체가 같은지를 비교
//        준비단계(prepare,given단계라고도 부름)
        Author author = Author.builder().name("abc").email("abc@naver.com").password("1234").role(Role.USER).build();
//        실행(execute,when)
        authorRepository.save(author);

//        검증(then)
        Author authorDb = authorRepository.findByEmail("abc@naver.com").orElse(null);
        Assertions.assertEquals(author.getEmail(),authorDb.getEmail()); // sout하지않고 굳이 assertion을 사용하는 이유는@test을 붙이고 실행하면 에러가 나
                                                                            //났을 때 빨간불이 들어오며 어디가 에러났는지 알려주기 때문
    }
}
