package com.taskmanager.auth.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthLogin {
    private String email;
   private  String password;
}
