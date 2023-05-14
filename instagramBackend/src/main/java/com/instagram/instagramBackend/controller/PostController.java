package com.instagram.instagramBackend.controller;

import com.instagram.instagramBackend.module.Post;
import com.instagram.instagramBackend.service.AuthenticationService;
import com.instagram.instagramBackend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    AuthenticationService authenticationService;

    //addPost
    @PostMapping("/addPost")
    public ResponseEntity<String> savePost(@RequestBody Post post, @RequestParam String userEmail , @RequestParam String token){
        HttpStatus status;
        String auth = "";
        if(authenticationService.authenticate(userEmail , token))
        {
            postService.savePostData(post);
            auth = " new Post Uploaded";
            status = HttpStatus.OK;
        }
        else
        {
            auth = "user not found please check th details";
            status = HttpStatus.FORBIDDEN;
        }


        return  new ResponseEntity<String>(auth , status);
    }


    //getPost
    @GetMapping()
    public ResponseEntity<List<Post>> getPost(@RequestParam String userEmail , @RequestParam String token){
        HttpStatus status;
        List<Post> posts = null;
        if(authenticationService.authenticate(userEmail,token))
        {
            posts = postService.getPost(token);
            status = HttpStatus.OK;
        }
        else
        {
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<List<Post>>(posts , status);
    }

}
