package com.example.Flower.repository;

import com.example.Flower.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);
    Optional<User> findByNickname(String nickname);
}
