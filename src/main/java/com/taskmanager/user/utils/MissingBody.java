package com.taskmanager.user.utils;
import java.util.ArrayList;
import java.util.List;

import com.taskmanager.user.dtos.UserDto;
public class MissingBody {
    
    public List<UserDto> missingFields(UserDto body){
        ArrayList missingFields = new ArrayList<String>();
        try{
            if(body.getName() != null || body.getName().isBlank()) throw new RuntimeException("Forneça o nome"); missingFields.add("name");
            if(body.getEmail() != null || body.getEmail().isBlank()) throw new RuntimeException("Forneça o email"); missingFields.add("email");
            if(body.getPassword() != null || body.getPassword().isBlank()) throw new RuntimeException("Forneça a senha");
            if(body.getRole() != null) throw new RuntimeException("Forneça a Role"); missingFields.add("role");
            return missingFields(body);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
        
        
    }

