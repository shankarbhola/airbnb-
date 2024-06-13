package com.airbnb.repository;

import com.airbnb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmailId(String username, String emailId);
    Optional<User> findByUsername(String username);

    User findByEmailId(String email);
}