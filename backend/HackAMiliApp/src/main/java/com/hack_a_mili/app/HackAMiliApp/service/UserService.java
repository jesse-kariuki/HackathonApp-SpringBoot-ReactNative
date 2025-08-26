package com.hack_a_mili.app.HackAMiliApp.service;

import com.hack_a_mili.app.HackAMiliApp.dao.UserRepository;
import com.hack_a_mili.app.HackAMiliApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user){

       User newUser = new User();
       newUser.setEmail(user.getEmail());
       newUser.setPassword(passwordEncoder.encode(user.getPassword()));


        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }





        return userRepository.save(newUser);
    }


    public User loginUser(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
