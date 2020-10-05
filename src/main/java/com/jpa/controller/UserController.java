package com.jpa.controller;

import com.jpa.entity.User;
import com.jpa.exception.ResourceNotFoundException;
import com.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UserController {
    @Autowired
    private UserRepository userRepository;

    //get all users
    @GetMapping("/all")
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }
    //get a user by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value="id") long userId){
        return  this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+ userId));
    }
    //create user
    @PostMapping("/create")
    public User createUser(@RequestBody User user){
        System.out.println("inside create user");
        return this.userRepository.save(user);
    }
    //update a user
    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long userId){
        User existingUser = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+ userId));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());

        this.userRepository.save(existingUser);
        return existingUser;
    }
    //delete a user
    @DeleteMapping("{/id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long userId){
        User existingUser = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+ userId));
        this.userRepository.delete(existingUser);

        return ResponseEntity.ok().build();
    }
}
