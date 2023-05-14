package com.instagram.instagramBackend.service;

import com.instagram.instagramBackend.dto.SignInInput;
import com.instagram.instagramBackend.dto.SignInOutput;
import com.instagram.instagramBackend.dto.SignUpInput;
import com.instagram.instagramBackend.dto.SignUpOutput;
import com.instagram.instagramBackend.module.AuthenticationToken;
import com.instagram.instagramBackend.module.Users;
import com.instagram.instagramBackend.repository.IAuthenticationTokenRepository;
import com.instagram.instagramBackend.repository.IUserRepository;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IAuthenticationTokenRepository iAuthenticationTokenRepository;

    @Autowired
    AuthenticationService authenticationService;

    //singUp//
    public SignUpOutput singUp(SignUpInput signUpDto) {
        //1st. check if user already exists or not using user Email
        Users users = iUserRepository.findFirstByUserEmail(signUpDto.getUserEmail());
        if(users != null) {
            throw new IllegalStateException("This UserEmail already Exists !!! .. Please try to Login");
        }

        //2nd. password encryption
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptPassword(signUpDto.getUserPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        //3rd. save the user data

        users = new Users(signUpDto.getUserFirstName() , signUpDto.getUserLastName(),signUpDto.getUserEmail() , encryptedPassword , signUpDto.getUserPhoneNumber() , signUpDto.getUserAge());
        iUserRepository.save(users);

        //4th. Token create
        AuthenticationToken token = new AuthenticationToken(users);
        authenticationService.saveToken(token);


        //5th. return singUpOutput
        return new SignUpOutput("User registered" , "Account Created successfully");
    }

//...........................................................................................................//
    //encryption method
    private String encryptPassword(String userPassword) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5") ;
        md5.update(userPassword.getBytes());
        byte[] digested = md5.digest();

        String hash = DatatypeConverter.printHexBinary(digested);
        return hash;
    }


//............................................................................................................//

    //signIn//
    public SignInOutput signIn(SignInInput signInInDto) {
        //1st. check email exists
        Users users = iUserRepository.findFirstByUserEmail(signInInDto.getUserEmail());

        if(users == null) {
            throw new IllegalStateException("This User is not Exists !!! .. Please try to Create account or please signUp");
        }

        //2nd. encrypt the password
        String encryptedPassword = null;

        try {
            encryptedPassword = encryptPassword( signInInDto.getUserPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


        //3rd. match it with database encrypted password
        boolean isPasswordValid = encryptedPassword.equals(users.getUserPassword());
        if(!isPasswordValid){
            throw new IllegalStateException("Incorrect Password !!! .. Please re-check");
        }

        //4th. figure out the token
        AuthenticationToken authToken = authenticationService.getToken(users);

        //5th. set up output response
        return new SignInOutput("Authentication successfully",authToken.getToken());
    }

//................................................................................................//
    public void updateUsers(Users users, String token) {
            Users users1 = iAuthenticationTokenRepository.findFirstByToken(token).getUsers();

        if(!(users.getUserFirstName().isEmpty())){
            users1.setUserFirstName(users.getUserFirstName());
        }
        if(( users.getUserLastName()!=null)){
            users1.setUserLastName(users.getUserLastName());
        }
        if((users.getUserEmail()!=null)){
            users1.setUserEmail(users.getUserEmail());
        }
        if((users.getUserPassword()!=null)){
            String encryptedPassword = null;

            try {
                encryptedPassword = encryptPassword(users.getUserPassword());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            users1.setUserPassword(encryptedPassword);
        }
        if((users.getUserPhoneNumber()!=null)){
            users1.setUserPhoneNumber(users.getUserPhoneNumber());
        }

        if(users.getUserAge()!=null){
            users1.setUserAge(users.getUserAge());
        }
        iUserRepository.save(users1);
    }
}
