package com.instagram.instagramBackend.service;

import com.instagram.instagramBackend.module.Post;
import com.instagram.instagramBackend.module.Users;
import com.instagram.instagramBackend.repository.IAuthenticationTokenRepository;
import com.instagram.instagramBackend.repository.IPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    IPostRepository iPostRepository;

    @Autowired
    IAuthenticationTokenRepository iAuthenticationTokenRepository;

    public void savePostData(Post post) {
        iPostRepository.save(post);
    }

    public List<Post> getPost(String token) {
        Users users = iAuthenticationTokenRepository.findFirstByToken(token).getUsers();
        List<Post> posts = iPostRepository.findByUsers(users);
        return posts;

    }
}
