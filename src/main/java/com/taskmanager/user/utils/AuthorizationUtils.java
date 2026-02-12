package com.taskmanager.user.utils;

import com.taskmanager.user.Role.Role;
public final class AuthorizationUtils  {
    private AuthorizationUtils() {
        
    }

        public static void isAdmin( Role role){
            if(role != Role.ADMIN){
                throw new RuntimeException("Acesso não autorizado");
            }

        } 
}
