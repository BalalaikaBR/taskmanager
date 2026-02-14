package com.taskmanager.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.taskmanager.task.repository.TaskRepository;
import com.taskmanager.task.dtos.TaskStatusResponse;
import com.taskmanager.task.entity.Task;
import com.taskmanager.exceptions.GenericException;
@Service
public class TaskHooks {
    private TaskService taskService;
    private GenericException error = new GenericException();
    public TaskHooks( TaskService taskService){
        this.taskService = taskService;
    }
        public List<Task> tasksCompleted(int page, int limit){
            try{
                
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


        public List<Task> taskPendente(int page, int limit){
            try{
                
                ArrayList pendingTask = new ArrayList<Task>();
                Page<Task> task = taskService.getAllTaks(page, limit);
               
                for(Task k : task.getContent()){
                    if(k.getCompleted() == false){
                        pendingTask.add(k);
                    }
                }
                return pendingTask;
            }catch(Exception e){
                  return error.handleException(e);
            }
        }

        public String taskStatus(int page, int limit){
            try{
                    int completas = 0;
                    int pendentes = 0; 

                 Page<Task> tasks = taskService.getAllTaks(page, limit);
                 for(Task t: tasks.getContent()){
                    if(t.getCompleted() == true){
                        completas++;
                    }
                    if(t.getCompleted() == false){
                        pendentes++;
                    }
                }
                return new TaskStatusResponse(completas, pendentes).toString();
            }catch(Exception e){
                  return error.handleException(e);
            }
        }
}
