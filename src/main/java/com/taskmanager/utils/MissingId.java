package com.taskmanager.utils;

public class MissingId {
    public void missingID(String id){
        try{
            if(id == null) throw new RuntimeException("Forneça o Id!");
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
}
