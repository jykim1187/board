package com.example.board.common.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//자바 버전과 스프링버전에 의해 조금 차이가 있는게 필터.
@Configuration
@EnableWebSecurity //spring security filter설정을 customizing하기 위한 어노테이션
@EnableGlobalMethodSecurity(prePostEnabled = true) //pre(true):사전검증, post(false):사후검증
public class SecurityConfig {
    @Bean
    public SecurityFilterChain myFileter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity  ////리턴하는 객체를 싱글톤 객체로 만들어 얘가 필터 역할을한다.
//                    csrf공격에 대한 설정은 하지 않겠다라는 의미
                .csrf().disable()
                //어떤 api요청은 허용하고 어떤 것은 허용하지 않겠다는 것,antMatchers안에 있는 url은 허용하고. 그외에 어떠한 리퀘스트에는  인증을받겠다
                .authorizeRequests().antMatchers("/", "/author/create", "/author/login").permitAll().anyRequest().authenticated()
                .and() //에러가 터지면 로그인페이지로 아래를 쓰겠다
                .formLogin().loginPage("/author/login")
//                   스프링에 사전에 구현되어있는 doLogin메서드 그대로 사용
                .loginProcessingUrl("/doLogin")
//                    다만, doLogin에 넘겨줄 email,password의 변수명은 별도 지정
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .build();

    }
    //위에 있는 것은 스프링필터에 구현되어 의존성주입할 곳이 없었는데
//    이거는 의존성 주입해야한다.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



}