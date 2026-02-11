package com.taskmanager.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.taskmanager.user.dtos.UserDto;
import com.taskmanager.user.entity.User;
import com.taskmanager.user.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public User createUser(@RequestBody UserDto user){
        try{
            return userService.createUser(user);
        }catch(Exception e){
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable  String id, @RequestBody UserDto user){
        try{
            return userService.updateUser(user, id);
        }catch(Exception e){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "update-partial/{id}", method = RequestMethod.PATCH)
    public User patchUser(@PathVariable  String id, @RequestBody UserDto user){
        try{
            return userService.patchUser(id, user);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable  String id){
        try{
            return userService.getUserById(id);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
     @GetMapping()
      public List<User> getAllUser(){
        try{
            return userService.getAllUser();
        }catch(Exception e){
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
      }
      @DeleteMapping("delete/{id}")
      public void deleteUser(@PathVariable String id){
        try{
            userService.deleteUser(id);
        }catch(Exception e){
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
      }

}
