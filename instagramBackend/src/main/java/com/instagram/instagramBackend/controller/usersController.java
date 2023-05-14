package com.instagram.instagramBackend.controller;

import com.instagram.instagramBackend.dto.SignInInput;
import com.instagram.instagramBackend.dto.SignInOutput;
import com.instagram.instagramBackend.dto.SignUpInput;
import com.instagram.instagramBackend.dto.SignUpOutput;
import com.instagram.instagramBackend.module.Users;
import com.instagram.instagramBackend.service.AuthenticationService;
import com.instagram.instagramBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class usersController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    //sign up
    @PostMapping("/signup")
    public SignUpOutput signUp(@RequestBody SignUpInput signUpDto){
        return userService.singUp(signUpDto);
    }

    //SingIn
    @PostMapping("/signing")
    public SignInOutput signup(@RequestBody SignInInput signInInDto){
        return  userService.signIn(signInInDto);
    }


    //update
    @PutMapping("update")
    public ResponseEntity<String> updateUserData(@RequestBody Users users, @RequestParam String userEmail , @RequestParam String token){
        HttpStatus status;
        String auth=null;

        if(authenticationService.authenticate(userEmail,token))
        {
            try{
                userService.updateUsers(users , token);
                status = HttpStatus.OK;
                auth = "user data updated successfully";
            }catch (Exception e){
                auth = "data provided is not correct check again";
                status = HttpStatus.BAD_REQUEST;
            }

        }
        else
        {
            auth = "User not found";
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<String>(auth , status);
    }
}
