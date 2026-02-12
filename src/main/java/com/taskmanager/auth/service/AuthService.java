package com.taskmanager.auth.service;

import org.springframework.stereotype.Service;

import com.taskmanager.auth.Jwt.JwtUtils;
import com.taskmanager.auth.dtos.AuthLogin;
import com.taskmanager.auth.dtos.JwtResponse;
import com.taskmanager.user.dtos.UserDto;
import com.taskmanager.user.entity.User;
import com.taskmanager.user.services.UserDetailService;
import com.taskmanager.user.services.UserService;
import com.taskmanager.exceptions.GenericException;
import org.springframework.security.core.userdetails.UserDetails;


@Service
public class AuthService {
    private UserService user;
    private JwtUtils jwt;
    private UserDetailService details;
    private GenericException error = new GenericException();
    public AuthService(JwtUtils jwt, UserService user, UserDetailService details){
        this.jwt = jwt;
        this.user = user;
        this.details = details;
    } 

    public User registerUser(UserDto body){
        try{
            User register = new User();
            register = user.createUser(body);
            return register;
        }catch(Exception e){
            return error.handleException(e);
        }
    }

public JwtResponse loginUser(AuthLogin login){

    try {

        UserDetails userDetails = details.loadUserByUsername(login.getEmail());

        String accessToken = jwt.generateJwtToken(userDetails.getUsername());
        String refreshToken = jwt.generateRefreshToken(login.getEmail());
        JwtResponse res = new JwtResponse();
        res.setAccesToken(accessToken);
        res.setRefreshToken(refreshToken);
        res.setUser(login);
        return res;
    } catch (Exception e) {
        return error.handleException(e);
    }
}

}
