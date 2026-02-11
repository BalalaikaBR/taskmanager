package com.taskmanager.user.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.taskmanager.user.entity.User;
import com.taskmanager.user.repository.UserRepository;
import com.taskmanager.user.dtos.UserDto;
import com.taskmanager.user.utils.MissingId;

@Service
public class UserService {
   private final UserRepository repo;   
   private PasswordEncoder passwordEncoder;
   private MissingId idIsMissing = new MissingId();
    public UserService(UserRepository repo, PasswordEncoder passwordEncoder){
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
   }

    public User createUser(UserDto body){

        try{
            User user = new User();
            user.setName(body.getName());
            user.setEmail(body.getEmail());
            user.setPassword(passwordEncoder.encode(body.getPassword()));
            return repo.save(user);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public User updateUser(UserDto body, String id) {
    idIsMissing.missingID(id);
    User user = repo.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado")); 

    user.setName(body.getName());
    user.setEmail(body.getEmail());
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
    throw new RuntimeException(e);
}
}

public User patchUser(String id, UserDto body){
    try{
       idIsMissing.missingID(id);
        User user = repo.findById(id).orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
            if(body.getName() != null) user.setName(body.getName());
            if(body.getEmail() != null) user.setEmail(body.getEmail());
            if(body.getPassword() != null) user.setPassword(passwordEncoder.encode(body.getPassword()));
            return repo.save(user);
    }catch(Exception e){
        throw new RuntimeException(e);
    }
}


public List<User> getAllUser(){
try{
    return repo.findAll();
}catch(Exception e){
    throw new RuntimeException(e);
}
}




}
