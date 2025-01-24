package com.example.board.post.repository;

import com.example.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Page<Post> findAll(Pageable pageable);

    Page<Post>findAllByAppointment(Pageable pageable, String appointment);

//    jpql을 사용한 일반 join
//    우리가 생각하는 db의 join은 fetch join이다.
//    일반 join은 필터링만 거는 느낌 그대로 n+1문제를 지니고 있다.
    @Query("select p from Post p inner join p.author")
    List<Post> findAlljoin();

//    jpql을 사용한 fetch join
//    SQL쿼리 select * from post p left join author a on a.id=p.author_id;
//    위에 보면 이 레포지토리가 아는 거는 Post엔티티밖에 없다. 따라서, p.author을 기준
//    fetch조인이라는거 는 프로그램 상에 있는 개념이지, db에 있는 개념은 아니다
    @Query("select p from Post p inner join fetch p.author")
    List<Post> findAllFetchJoin();
}
