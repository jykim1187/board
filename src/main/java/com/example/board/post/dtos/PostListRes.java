package com.example.board.post.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostListRes {
//  아아디,타이틀,작성자 이메일
    private Long id;
    private String title;
    private String authorEmail;

}
