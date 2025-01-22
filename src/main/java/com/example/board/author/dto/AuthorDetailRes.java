package com.example.board.author.dto;

import com.example.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorDetailRes {
//    id, name, email, password, role, postCount, createdTime
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private int PostCount;
    private LocalDateTime CreatedTime;


}
