package com.taskmanager.user.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.taskmanager.user.entity.User;
import com.taskmanager.user.repository.UserRepository;
import com.taskmanager.user.dtos.UserDto;
import com.taskmanager.user.utils.MissingId;
import com.taskmanager.user.utils.MissingBody;
import com.taskmanager.exceptions.GenericException;

@Service
public class UserService {
   private final UserRepository repo;   
   private PasswordEncoder passwordEncoder;
   private MissingId idIsMissing = new MissingId();
   private MissingBody missingBody = new MissingBody();
   private GenericException error = new GenericException();
    public UserService(UserRepository repo, PasswordEncoder passwordEncoder){
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
   }

    public User createUser(UserDto body){

        try{
            User user = new User();
            missingBody.missingFields(body);
            user.setName(body.getName());
            user.setEmail(body.getEmail());
            user.setRole(body.getRole());
            user.setPassword(passwordEncoder.encode(body.getPassword()));
            return repo.save(user);
        }catch(Exception e){
           return error.handleException(e);
        }
    }

    public User updateUser(UserDto body, String id) {
    idIsMissing.missingID(id);
    missingBody.missingFields(body);
    User user = repo.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado")); 

    user.setName(body.getName());
    user.setEmail(body.getEmail());
    user.setRole(body.getRole());
    user.setPassword(passwordEncoder.encode(body.getPassword()));

    return repo.save(user);
}

public void deleteUser(String id){
    
    idIsMissing.missingID(id);
    repo.findById(id).orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
     repo.deleteById(id);
   
}
public User getUserById(String id){
    try{
        idIsMissing.missingID(id);
        return repo.findById(id).orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
    }catch(Exception e){
   return error.handleException(e);
}
}

public User patchUser(String id, UserDto body){
    try{
       idIsMissing.missingID(id);
         missingBody.missingFields(body);
        User user = repo.findById(id).orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
            if(body.getName() != null) user.setName(body.getName());
            if(body.getEmail() != null) user.setEmail(body.getEmail());
            if(body.getPassword() != null) user.setPassword(passwordEncoder.encode(body.getPassword()));
            if(body.getRole() != null) user.setRole(body.getRole());
            return repo.save(user);
    }catch(Exception e){
      return error.handleException(e);
    }
}


public List<User> getAllUser(){
try{
    return repo.findAll();
}catch(Exception e){
     return error.handleException(e);
}
}




}
