package com.example.board.post.domain;

import com.example.board.author.domain.Author;
import com.example.board.common.domain.BaseTimeEntity;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostUpdatedReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 3000)
    private String contents;
    private String appointment;
    private LocalDateTime appointmentTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;


    public PostListRes toListFromEntity(){
        return new PostListRes(this.id,this.title,this.author.getEmail());
    }

    public PostDetailRes toDetailDtoFromEntity(){
        return new PostDetailRes(this.id,this.title,this.contents,this.author.getEmail(),this.getCreatedTime(),this.getUpdatedTime());
    }

    public void PostUpdate(PostUpdatedReq postUpdatedReq){
        this.title = postUpdatedReq.getTitle();
        this.contents = postUpdatedReq.getContents();
    }

    public void updateAppointment(String appointment){
        this.appointment = appointment;
    }

}
