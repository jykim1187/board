package com.example.board.author.domain;

import com.example.board.author.dto.AuthorDetailRes;
import com.example.board.author.dto.AuthorListRes;
import com.example.board.author.dto.AuthorUpdateReq;
import com.example.board.common.domain.BaseTimeEntity;
import com.example.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class Author extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 30, nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
//    enum은 기본적으로 숫자값으로 db에 들어감으로, 별도로 String타입으로 지정이 필요.
    @Enumerated(EnumType.STRING)
//    여기서 role의 기본값을 설정하지 않는이유는 기본값을 설정해도 사용자로부터 받는 SaveReq객체로 부터 null을 받아버리니까 의미가 없음.(그래서 savereq객체에다 검)
    private Role role;
//  OneToMany에서는 기본값이 fetch lazy라 별도의 설정은 필요없다
//  mappedBy에 ManyToOne쪽의 변수명을 문자열로 지정한다
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL) //Post엔티티에 조인컬럼한 변수명을 입력,db에서는 자식테이블에 cascade설정을 걸지만  스피
//    빌더패턴에서 변수 초기화(Default값)시 Builder.Default어노테이션을 사용해야한다.
    @Builder.Default
    private List<Post> posts = new ArrayList<>();







    public AuthorListRes listDtoFromEntity(){
        return new AuthorListRes(this.id,this.name,this.email);
    }

    public AuthorDetailRes toDetailFromEntity(){
        return new AuthorDetailRes(this.id,this.name,this.email,this.password,this.role,this.posts.size(),this.getCreatedTime());
    }

    public void updateProfile(AuthorUpdateReq authorUpdateReq){
        this.name = authorUpdateReq.getName();
        this.password = authorUpdateReq.getPassword();
    }

}
