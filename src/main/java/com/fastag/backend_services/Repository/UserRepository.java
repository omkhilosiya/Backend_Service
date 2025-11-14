package com.fastag.backend_services.Repository;

import com.fastag.backend_services.Model.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
