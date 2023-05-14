package com.instagram.instagramBackend.service;

import com.instagram.instagramBackend.module.AuthenticationToken;
import com.instagram.instagramBackend.module.Users;
import com.instagram.instagramBackend.repository.IAuthenticationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    IAuthenticationTokenRepository iAuthenticationTokenRepository;

    public void saveToken(AuthenticationToken token) {
        iAuthenticationTokenRepository.save(token);
    }

    public AuthenticationToken getToken(Users users) {
        return iAuthenticationTokenRepository.findByUsers(users);
    }

// authentication for update users details
    public boolean authenticate(String userEmail, String token) {
        if(token==null && userEmail==null){
            return false;
        }
        AuthenticationToken authToken = iAuthenticationTokenRepository.findFirstByToken(token);
        if(authToken==null){
            return false;
        }
        String mailBody = authToken.getUsers().getUserEmail();

       return mailBody.equals(userEmail);
    }
}
