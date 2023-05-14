package com.instagram.instagramBackend.repository;

import com.instagram.instagramBackend.module.AuthenticationToken;
import com.instagram.instagramBackend.module.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthenticationTokenRepository extends JpaRepository<AuthenticationToken , Long> {
    AuthenticationToken findByUsers(Users users);

    AuthenticationToken findFirstByToken(String token);
}
