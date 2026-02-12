package com.taskmanager.auth.dtos;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
     private String accesToken;
     private String refreshToken;
     private AuthLogin user;
     private String type = "Bearer";
}
