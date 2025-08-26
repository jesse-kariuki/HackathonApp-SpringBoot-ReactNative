package com.hack_a_mili.app.HackAMiliApp.service;

import com.hack_a_mili.app.HackAMiliApp.dao.DomainRepository;
import com.hack_a_mili.app.HackAMiliApp.dao.UserRepository;
import com.hack_a_mili.app.HackAMiliApp.entity.Domain;
import com.hack_a_mili.app.HackAMiliApp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DomainService {

    private final DomainRepository domainRepository;
    private final UserRepository userRepository;

    public List<Domain> getMyDomains(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return domainRepository.findByOwnerEmail(user.getEmail());
    }



}
