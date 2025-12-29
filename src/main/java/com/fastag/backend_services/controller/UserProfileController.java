package com.fastag.backend_services.controller;


import com.fastag.backend_services.Model.User;
import com.fastag.backend_services.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;


    //USER DETAILS WE GET FROM HERE
    @GetMapping("/getdetails/{userId}")
    public ResponseEntity<User> getUserDetails(@PathVariable String userId){
        User user = userRepository.findByUserId(userId);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
         return  ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
