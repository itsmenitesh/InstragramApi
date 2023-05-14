package com.instagram.instagramBackend.module;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private String postData;

   @ManyToOne(fetch = FetchType.LAZY)
    private Users users;
}
