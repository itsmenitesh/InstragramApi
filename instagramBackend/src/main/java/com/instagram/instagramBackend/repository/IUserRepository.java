package com.instagram.instagramBackend.repository;

import com.instagram.instagramBackend.module.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<Users , Long> {


    Users findFirstByUserEmail(String userEmail);
}
