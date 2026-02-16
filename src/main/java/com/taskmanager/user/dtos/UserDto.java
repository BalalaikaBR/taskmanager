package com.taskmanager.user.dtos;

import java.util.ArrayList;
import java.util.List;

import com.taskmanager.user.Role.Role;
import com.taskmanager.user.entity.User;

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
    private List friends = new ArrayList<User>();
}
