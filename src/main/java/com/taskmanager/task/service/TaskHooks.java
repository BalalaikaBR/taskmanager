package com.taskmanager.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.taskmanager.task.repository.TaskRepository;
import com.taskmanager.task.entity.Task;
import com.taskmanager.exceptions.GenericException;
@Service
public class TaskHooks {
    private TaskService taskService;
    private GenericException error = new GenericException();
    public TaskHooks( TaskService taskService){
        this.taskService = taskService;
    }
        public List<Task> tasksCompleted(){
            try{
                int page = 1;
                int limit = 100;
                ArrayList taskCompleted = new ArrayList<Task>();
                Page<Task> task = taskService.getAllTaks(page, limit);
                for(Task k : task.getContent()){
                    if(k.getCompleted() == true){
                        taskCompleted.add(k);
                    }
                }
                return taskCompleted;

            }catch(Exception e){
                return error.handleException(e);
            }
        }


        public List<Task> taskPendente(){
            try{
                int page = 1;
                int limit = 100;
                ArrayList taskPendente = new ArrayList<Task>();
                Page<Task> task = taskService.getAllTaks(page, limit);
               
                for(Task k : task.getContent()){
                    if(k.getCompleted() == false){
                        taskPendente.add(k);
                    }
                }
                return taskPendente();
            }catch(Exception e){
                  return error.handleException(e);
            }
        }
}
