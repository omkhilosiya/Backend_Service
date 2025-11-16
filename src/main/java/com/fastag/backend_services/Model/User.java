package com.fastag.backend_services.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

    @Data
    @Document(collection = "users")
    public class User {
        @Id
        private String id;
        private String name;
        private String email;
        private String password;
        private int phone;
        private String gender;
        private String entityId;
        private String parentId;
        private String profileImage;
        private String roles;
        private List<String> permissions;
        private List<String> serviceAccess;
        private ServicePrices servicePrices;


        public User(String email, String password, String roles) {
            this.email = email;
            this.password = password ;
            this.roles = roles;
        }
    }
