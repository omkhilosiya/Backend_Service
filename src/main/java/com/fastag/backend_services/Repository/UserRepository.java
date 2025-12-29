package com.fastag.backend_services.Repository;

import com.fastag.backend_services.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;  
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

    List<User> findByRoles(String roles);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    User findByUsername(String username);
    Page<User> findAll(Pageable pageable);

    List<User> findByNameRegexIgnoreCaseOrEmailRegexIgnoreCaseOrPhoneRegexIgnoreCaseOrUsernameRegexIgnoreCase(
            String name, String email, String phone, String username);

    // all users
    List<User> findAll();

    // find user by userId (string)
    User findByUserId(String userId);

}
