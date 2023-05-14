package com.instagram.instagramBackend.repository;

import com.instagram.instagramBackend.module.Post;
import com.instagram.instagramBackend.module.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post , Long> {
    List<Post> findByUsers(Users users);
}
