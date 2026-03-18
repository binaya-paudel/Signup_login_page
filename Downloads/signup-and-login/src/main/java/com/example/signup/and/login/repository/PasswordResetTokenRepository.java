package com.example.signup.and.login.repository;

import com.example.signup.and.login.entity.PasswordResetTokenEntity;
import com.example.signup.and.login.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long>{
    Optional<PasswordResetTokenEntity> findById(Long aLong);
    Optional<PasswordResetTokenEntity> findByToken(String token);

    @Modifying
    @Transactional
    void deleteByUser(UsersEntity user);

}
