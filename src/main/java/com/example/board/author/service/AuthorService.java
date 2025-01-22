package com.example.board.author.service;

import com.example.board.author.domain.Author;
import com.example.board.author.dto.AuthorDetailRes;
import com.example.board.author.dto.AuthorListRes;
import com.example.board.author.dto.AuthorSaveReq;
import com.example.board.author.dto.AuthorUpdateReq;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;

    public AuthorService(AuthorRepository authorRepository, PostRepository postRepository) {
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
    }

    public void save(AuthorSaveReq authorSaveReq) {

        if (authorRepository.findByEmail(authorSaveReq.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }
        authorRepository.save(authorSaveReq.toEntity());
//        cascade를 활용하지 않고, 별도로 post데이터만드는 경우
//        Author author =authorRepository.save(authorSaveReq.toEntity());
//        postRepository.save(Post.builder().title("반갑습니다").contents("처음입니다").
//                author(author).build());

////        cascade를 활용해서, post데이터를 함께 만드는 경우
//        Author author = Author.builder().name(authorSaveReq.getName())
//                .email(authorSaveReq.getEmail())
//                .password(authorSaveReq.getPassword())
//                .role(authorSaveReq.getRole())
//                .build();
//        author.getPosts().add(Post.builder().title("반가워요").author(author).build());
////        그런데 getPosts()까지는 아무것도 없어서 없는 거에 add를 하니까 에러가 난다. 따라서 Author에가서 초기화 필요
//        author.getPosts().add(Post.builder().title("반가워요2").author(author).build());
//        authorRepository.save(author);
        //author에 cascade_ALL설정이 걸려있어 author를 만들면서 post를 추가해도 자연스레 post도 됨.(all은 persist와 remove가 포함. 여기선 persist적용)
        // post를 생성하는 시점에 author가 아직db에 저장되지 않은 것으로 보여지나, jpa가 author와 post를 save하는 시점에 선후관계를 맞춰줌
        // 원래라면 author를 세이브하고 post객체를 만들수 있지만 jpa가 db에 코드의 순서와 상관없이 save를 먼저 하기 때문에 가능
        //저번 수업때 update(비밀번호 수정) 때 다시 save하는 상황에서 jpa의 영속성 때문에 save를 삭제해도 자동으로 가능하다 했는데.
        // 기존에 있던 객체를  update에서만 가능한 상황으로 여기서와는 연관x


    }

    public List<AuthorListRes> findAll() {
        return authorRepository.findAll().stream().map(a -> a.listDtoFromEntity()).collect(Collectors.toList());
    }


    public void deleteById(Long id) {
        authorRepository.delete(authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("no id")));
   //deleteById할 수도 있으나 좀 더 객체 지향적으로
    }

    public AuthorDetailRes fingById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("no id")).toDetailFromEntity();
    }

    public void update(Long id, AuthorUpdateReq authorUpdateReq) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("no id"));
//내 방식
//                Author updatedAuthor = Author.builder().id(author.getId()).name(authorUpdateDt0.getName()).email(author.getEmail())
//                .password(authorUpdateDt0.getPassword()).role(author.getRole())
//                .posts(author.getPosts())
//                .build();
////                여기서는 더티캐싱 적용x이므로 save무조건
//        authorRepository.save(updatedAuthor);

//        강사님 방식
        author.updateProfile(authorUpdateReq);
//       기존객체에 변경이 발생할 경우, 별도의 save없이도 jpa가 엔티티의 변경을 자동인지하고, 변경사항을 db반영
//        이를 dirtychecking이라 부르고, 반드시 transactional어노테이션이 있을 경우에 동작한다.
        authorRepository.save(author);


    }
}
