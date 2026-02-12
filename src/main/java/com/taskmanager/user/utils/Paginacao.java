package com.taskmanager.user.utils;

public class Paginacao {
   
    public Integer paginacao(int page, int limit){
        Integer pag = (page - 1) * limit;
        if(pag < 1)throw new RuntimeException("A quantidade de páginas devem ser maiores que 1 para funcionar");
            
        if (limit < 1) {
            throw new IllegalArgumentException("Limit deve ser maior ou igual a 1");
        }
        return pag;
    }
}
