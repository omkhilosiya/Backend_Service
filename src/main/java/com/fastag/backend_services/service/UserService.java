package com.fastag.backend_services.service;

import com.fastag.backend_services.Model.User;
import com.fastag.backend_services.Repository.UserRepository;
import com.fastag.backend_services.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<UserResponse> getUsers(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).descending()
        );

        Page<User> users = userRepository.findAll(pageable);

        return users.map(this::mapToResponse);
    }

    private UserResponse mapToResponse(User user) {
        UserResponse res = new UserResponse();

        res.setId(user.getId());
        res.setUserId(user.getUserId());
        res.setName(user.getName());
        res.setUsername(user.getUsername());
        res.setStatus(user.isStatus());
        res.setAge(user.getAge());
        res.setEmail(user.getEmail());
        res.setPhone(user.getPhone());
        res.setGender(user.getGender());
        res.setEntityId(user.getEntityId());
        res.setParentId(user.getParentId());
        res.setProfileImage(user.getProfileImage());
        res.setRoles(user.getRoles());
        return res;
    }
}
