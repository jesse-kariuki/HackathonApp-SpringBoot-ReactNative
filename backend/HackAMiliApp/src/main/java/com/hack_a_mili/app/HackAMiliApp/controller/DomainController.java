package com.hack_a_mili.app.HackAMiliApp.controller;

import com.hack_a_mili.app.HackAMiliApp.dao.DomainRepository;
import com.hack_a_mili.app.HackAMiliApp.dto.DomainRequest;
import com.hack_a_mili.app.HackAMiliApp.entity.Domain;
import com.hack_a_mili.app.HackAMiliApp.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/domains")
@RequiredArgsConstructor
public class DomainController {

    private final DomainRepository domainRepository;
    private final DomainService domainService;

//    @Autowired
//    public DomainController(DomainRepository domainRepository) {
//        this.domainRepository = domainRepository;
//    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchDomain(@RequestParam String domain) {
        Map<String, Object> response = new HashMap<>();
        response.put("domain", domain);

        boolean exists = domainRepository.existsByName(domain);

        if (exists) {
            response.put("available", false);
            response.put("message", "Domain is already registered");
        } else {
            response.put("available", true);
            response.put("message", "Domain is available");
        }

        return ResponseEntity.ok(response);
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerDomain(@RequestBody DomainRequest domainRequest) {
        Map<String, Object> response = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();



        if (domainRepository.existsByName(domainRequest.getName())) {
            response.put("success", false);
            response.put("message", "Domain is already registered!");
            return ResponseEntity.badRequest().body(response);
        }

        Domain newDomain = Domain.builder()
                .name(domainRequest.getName())
                .ownerEmail(currentUserEmail)
                .ns1(domainRequest.getNs1() != null ? domainRequest.getNs1() : "ns1.centralnic.com")
                .ns2(domainRequest.getNs2() != null ? domainRequest.getNs2() : "ns2.centralnic.com")
                .build();

        domainRepository.save(newDomain);

        response.put("success", true);
        response.put("message", "Domain registered successfully!");
        response.put("domain", newDomain);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-domains")
    public ResponseEntity<List<Domain>> getMyDomains(Authentication authentication) {
        String email = authentication.getName();
        List<Domain> myDomains = domainService.getMyDomains(email);
        return ResponseEntity.ok(myDomains);
    }

}
