package com.fastag.backend_services.dto;
import lombok.Data;


@Data
public class UserResponse {

    private String id;
    private String userId;
    private String name;
    private String username;
    private boolean status;
    private int age;
    private String email;
    private String phone;
    private String gender;
    private String entityId;
    private String parentId;
    private String profileImage;
    private String roles;
//    private LocalDateTime createdDate;
//    private List<String> permissions;
//    private List<String> serviceAccess;
//    private ServicePrices servicePrices;
//    private Wallet wallet;
}
