package com.taskmanager.user.dtos;

import com.taskmanager.user.Role.Role;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String name;
    private String email;
    private String password;
    private Role role;
}
