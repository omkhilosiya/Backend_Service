package com.fastag.backend_services.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

    @Data
    @Document(collection = "users")
    public class User {
        @Id
        private String id;
        private String fullName;
        private String email;
        private String password;

}
