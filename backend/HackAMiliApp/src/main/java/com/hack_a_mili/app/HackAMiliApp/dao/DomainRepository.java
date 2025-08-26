package com.hack_a_mili.app.HackAMiliApp.dao;

import com.hack_a_mili.app.HackAMiliApp.entity.Domain;
import com.hack_a_mili.app.HackAMiliApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DomainRepository extends JpaRepository<Domain, Long> {

    <Optional> Domain findByName(String domainName);
    boolean existsByName(String domainName);
    List<Domain> findByOwnerEmail(String email);

}
