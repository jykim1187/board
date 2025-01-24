package com.example.board.post.service;

import com.example.board.author.domain.Author;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.domain.Post;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.dtos.PostUpdatedReq;
import com.example.board.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    public PostService(PostRepository postRepository,AuthorRepository authorRepository){
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }


    public void save(PostSaveReq postSaveReq){
        Author author = authorRepository.findByEmail(postSaveReq.getEmail()).orElseThrow(()->new EntityNotFoundException("no id"));
        LocalDateTime appointmentTime = null;
       if(postSaveReq.getAppointment().equals("Y")){
           if(postSaveReq.getAppointmentTime().isEmpty() || postSaveReq.getAppointmentTime()==null){
               throw new IllegalArgumentException("시간이 비어져있습니다");
           } else {
               DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
               appointmentTime = LocalDateTime.parse(postSaveReq.getAppointmentTime(), dateTimeFormatter);
               LocalDateTime now = LocalDateTime.now();
               if (appointmentTime.isBefore(now)) {
                   throw new IllegalArgumentException("시간이 과거입니다");
               }
           }
       }
        postRepository.save(postSaveReq.toEntity(author,appointmentTime));
    }


    public List<PostListRes> findAll(){
        return postRepository.findAll().stream().map(p->p.toListFromEntity()).collect(Collectors.toList());
    }

    public Page<PostListRes> findAllPaging(Pageable pageable){
        Page<Post> pagePosts = postRepository.findAllByAppointment(pageable,"N");
        return pagePosts.map(p->p.toListFromEntity());
    }

    public List<PostListRes> listFetchJoin(){
//        일반 JOIN :  author를 join해서 post를 조회하긴 하나, author의 데이터는 실제 참조할 때 쿼리가 n번 발생
//        List<Post> postList = postRepository.findAlljoin(); //쿼리 1번

//      FETCH JOIN :author를 join해서 조회하고, author의 데이터도 join 시점에서 가져옴,쿼리 1번 발생
        List<Post> postList = postRepository.findAllFetchJoin();
        return postList.stream().map(p->p.toListFromEntity()).collect(Collectors.toList()); //쿼리 n번

    }

    public PostDetailRes findById(Long id){
       return postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("the post does not exist")).toDetailDtoFromEntity();
    }

    public void update(Long id, PostUpdatedReq postUpdatedReq){
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("the post does not exist"));
        post.PostUpdate(postUpdatedReq);
        postRepository.save(post);
    }

    public void deleteById(Long id){
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("the post does not exist"));
        postRepository.delete(post);
    }
}
