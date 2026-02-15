package com.taskmanager.task.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TaskStatusResponse {
    private int completadas;
    private int pendentes;
   
    @Override
    public String toString(){
        return "Completadas: " + completadas + 
        "\nPendentes: " + pendentes;
    } 

}
