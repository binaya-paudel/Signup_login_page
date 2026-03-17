package com.example.signup.and.login.repository;

import com.example.signup.and.login.entity.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {

Optional<UsersEntity> findByEmail(String email);

Optional<UsersEntity> findByPhoneNumber(Long phoneNumber);
}
