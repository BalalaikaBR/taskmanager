package com.taskmanager.user.utils;
import java.util.ArrayList;

public class IsEmail{ 
    public Boolean isEmail(String email){ 
        boolean condicao = true;
        if(!email.contains("@") || !email.contains(".com")){
            //condicao = false;
            throw new RuntimeException("Não é email");
        }
            Integer indexArroba = email.indexOf("@");
            Integer indexCom = email.indexOf(".com");

            if(indexArroba > indexCom){
                //condicao = false;
                throw new RuntimeException("O @ deve vir antes do .com");
            }
            return condicao;
        }
}