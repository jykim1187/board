package com.example.board.common.service;

import com.example.board.author.domain.Author;
import com.example.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

//1.doLogin 호출시
//2.스프링에서 구현한 doLogin메서드 내에서 loadUserByUsername을 실행
//3.loadUserByUsername에서 DB에서 조회한 user객체를 만들어서 return
//4.스프링에서 DB의 user객체와 사용자가 입력한 email/password를 비교
//5.검증이 완료되면, DB의 user객체를 Authentification객체에 저장(중요)
@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Author author = authorRepository.findByEmail(username).orElseThrow(()->new EntityNotFoundException("없다"));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + author.getRole()));
            return new User(author.getEmail(), author.getPassword(), authorities ); //user객체는 3가지 요소로 구성 : 1.username(email) 2.password 3.role
    }
}
