package com.hack_a_mili.app.HackAMiliApp.dao;

import com.hack_a_mili.app.HackAMiliApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}
