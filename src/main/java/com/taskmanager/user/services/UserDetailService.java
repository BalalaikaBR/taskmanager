package com.taskmanager.user.services;

import com.taskmanager.user.Role.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taskmanager.user.entity.User;
import com.taskmanager.user.repository.UserRepository;
import com.taskmanager.exceptions.GenericException;

//import lombok.RequiredArgsConstructor;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository repo;
  //  private GenericException error = new GenericException();
        public UserDetailService(UserRepository repo){
            this.repo = repo;
        }

        @Override
       public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException {
            
                User user = repo.findByEmail(email).orElseThrow(()-> new RuntimeException("Usuário não encontrado"));

                 return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(
                    new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
            ) 
                .build();
                
          
        }

}
