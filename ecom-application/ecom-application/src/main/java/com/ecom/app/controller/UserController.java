package com.ecom.app.controller;

import com.ecom.app.dto.UserRequest;
import com.ecom.app.dto.UserResponse;
import com.ecom.app.model.User;
import com.ecom.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){

        return new ResponseEntity<>(userService.fetchAllUsers() , HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        System.out.println("Email: " + userRequest.getEmail());
        System.out.println("Phone: " + userRequest.getPhone());
        System.out.println("FirstName: " + userRequest.getFirstName());
        System.out.println("LastName: " + userRequest.getLastName());
        System.out.println("LastName: " + userRequest.getAddress().getCountry());
        userService.addUser(userRequest);
        return ResponseEntity.ok("User added successfully");
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<String>  updateUser( @PathVariable Long id,@RequestBody UserRequest updatedUser ){

        boolean updated =userService.updateUser(id ,updatedUser  );
        if(updated){
            return ResponseEntity.ok("user updated succsessfuly");
        }
        else {
            return new  ResponseEntity<>("user updated is fail" , HttpStatus.NOT_MODIFIED);
        }
    }

}
