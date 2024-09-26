package com.cloud4.post.service;

import com.cloud4.post.domain.Post;
import com.cloud4.post.dto.PostDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    // 임시 db
    List<Post> data = new ArrayList<>();

    private Long generateId() {
        if (data.isEmpty()) {
            return 1L;
        }
        return data.get(data.size() - 1).getId() + 1;
    }

    public List<Post> getAllPosts() {
//        data.add(new Post("제목", "블로그 내용"));
        return data;
    }

    public Post savePost(PostDTO postDTO) {
        Post post = new Post(postDTO.getTitle(), postDTO.getContent());
        post.setId(generateId());
        data.add(post);
        return post;
    }

    public Post updatePost(Long id, PostDTO postDTO) {
        return data
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(
                        p -> {
                            p.setTitle(postDTO.getTitle());
                            p.setContent(postDTO.getContent());
                            return p;
                        }
                ).orElse(null);
    }

    // Todo -> 인텔리제이 기능 : 내가 구현해야 할 부분 표시
    public void deletePost(Long id) {
        data.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .ifPresent(p -> data.remove(p));
    }
}