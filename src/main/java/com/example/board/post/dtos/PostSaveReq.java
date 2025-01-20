package com.example.board.post.dtos;

import com.example.board.author.domain.Author;
import com.example.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostSaveReq {
//타이틀,콘텐츠,이메일
    @NotEmpty
    private String title;
    private String contents;
    @NotEmpty
    private String email;

    public Post toEntity(Author author){
        return Post.builder().title(this.title).contents(this.contents).author(author).build();
    }

}
