package com.product.acl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public ResponseEntity<?> createUser(User user) {
       Optional<User> user1= this.userRepository.findByUsername(user.getUsername());
       if(user1.isPresent()){
           return ResponseEntity.badRequest().body("User already exists");
       }
       else{
           this.userRepository.save(user);
           return ResponseEntity.ok().body("User created successfully");
       }
    }
}
