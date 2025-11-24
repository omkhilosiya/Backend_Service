package com.fastag.backend_services.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

    @Data
    @Document(collection = "hahaha")
    @AllArgsConstructor
    @NoArgsConstructor
    public class User implements UserDetails {
        @Id
        private String id;
        private String userId;
        private String name;
        private String username;
        private boolean status;
        private int age;
        private String email;
        private String password;
        private String phone;
        private String gender;
        private String entityId;
        private String parentId;
        private String profileImage;
        private String roles;
        private LocalDateTime createdDate;
        private List<String> permissions;
        private List<String> serviceAccess;
        private ServicePrices servicePrices;

        @DBRef
        private Wallet wallet;


        public User(String email, String password, String roles) {
            this.email = email;
            this.password = password ;
            this.roles = roles;
        }




        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(this.roles));
        }


        @Override
        public String getUsername() {
            return this.email;
        }

        @Override
        public boolean isAccountNonExpired() {
            return UserDetails.super.isAccountNonExpired();
        }

        @Override
        public boolean isAccountNonLocked() {
            return UserDetails.super.isAccountNonLocked();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return UserDetails.super.isCredentialsNonExpired();
        }

        @Override
        public boolean isEnabled() {
            return UserDetails.super.isEnabled();
        }

    }
