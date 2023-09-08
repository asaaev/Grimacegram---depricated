package com.grimacegram.grimacegram.user.repository;

import com.grimacegram.grimacegram.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
