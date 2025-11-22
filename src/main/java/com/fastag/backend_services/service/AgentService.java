package com.fastag.backend_services.service;

import com.fastag.backend_services.Model.User;
import com.fastag.backend_services.Repository.UserRepository;
import com.fastag.backend_services.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AgentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    public Map<String, Object> getAgents(String search) {

        List<User> users;

        if (search == null || search.trim().isEmpty()) {
            users = userRepository.findAll();
        } else {
            String pattern = ".*" + search + ".*";
            users = userRepository
                    .findByNameRegexIgnoreCaseOrEmailRegexIgnoreCaseOrPhoneRegexIgnoreCaseOrUsernameRegexIgnoreCase(
                            pattern, pattern, pattern, pattern);
        }

        // attach wallet to each user
        for (User user : users) {
            walletRepository.findByUserId(user.getUserId())
                    .ifPresent(user::setWallet);
        }

        // AGENT AND SUBAGENT LISTS
        List<User> agents = users.stream()
                .filter(u -> u.getWallet() != null &&
                        "AGENT".equalsIgnoreCase(u.getWallet().getType()))
                .toList();

        List<User> subAgents = users.stream()
                .filter(u -> u.getWallet() != null &&
                        "SUBAGENT".equalsIgnoreCase(u.getWallet().getType()))
                .toList();

        // ACTIVE / INACTIVE COUNTS
        long activeAgents = agents.stream().filter(User::isStatus).count();
        long inactiveAgents = agents.stream().filter(u -> !u.isStatus()).count();

        long activeSubAgents = subAgents.stream().filter(User::isStatus).count();
        long inactiveSubAgents = subAgents.stream().filter(u -> !u.isStatus()).count();

        // BUILD RESPONSE
        Map<String, Object> resp = new HashMap<>();
        resp.put("totalAgents", agents.size());
        resp.put("activeAgents", activeAgents);
        resp.put("inactiveAgents", inactiveAgents);
        resp.put("totalSubAgents", subAgents.size());
        resp.put("activeSubAgents", activeSubAgents);
        resp.put("inactiveSubAgents", inactiveSubAgents);
        resp.put("agents", agents);
        resp.put("subAgents", subAgents);
        return resp;
    }
}

