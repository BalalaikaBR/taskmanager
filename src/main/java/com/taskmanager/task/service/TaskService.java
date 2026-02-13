package com.taskmanager.task.service;
import com.taskmanager.exceptions.GenericException;
import com.taskmanager.task.dtos.TaskDto;
import com.taskmanager.task.dtos.UserTask;
import com.taskmanager.task.entity.Task;
import com.taskmanager.task.repository.TaskRepository;
import com.taskmanager.user.entity.User;
import com.taskmanager.user.repository.UserRepository;
import com.taskmanager.utils.MissingId;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service 
public class TaskService {
    private TaskRepository repo;
    private GenericException error = new GenericException();
    private MissingId isMissing = new MissingId();
    private UserRepository userRepo;
    public TaskService(TaskRepository repo, UserRepository userRepo){
        this.repo = repo;
        this.userRepo = userRepo;
    }
    public Page<Task> getAllTaks(int page, int limit){
        try{
            Pageable pagination = PageRequest.of(page - 1, limit);
            return repo.findAll(pagination);
        }catch(Exception e){
            return error.handleException(e);
        }
    }
    public Task createTask(TaskDto task){
        try{    
             User user = userRepo.findById(task.getUserId())
    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                Task newTask = new Task();
                newTask.setTask(task.getTask());
                newTask.setStatus(task.getStatus());
                newTask.setCompleted(task.getCompleted());
                newTask.setUser(user);
                return repo.save(newTask);
        }catch(Exception e){
            return error.handleException(e);
        }
}

    public Task getTaskById(String id){
        isMissing.missingID(id);
        try{
            return repo.findById(id).orElseThrow(()-> new RuntimeException("Id não encontrado!"));

        }catch(Exception e){
            return error.handleException(e);
        }
    }
    public Task patchTasks(String id, TaskDto body){
        try{
            isMissing.missingID(id);
            Task task = repo.findById(id).orElseThrow(()-> new RuntimeException("Task não encontrada"));
            if(body.getTask() != null) task.setTask(body.getTask()); 
            if(body.getCompleted() != null) task.setCompleted(body.getCompleted());
            if(body.getStatus() != null) task.setStatus(body.getStatus());
            return repo.save(task);
        }catch(Exception e){
              return error.handleException(e);
        }
    }

     public Task update(String id, TaskDto body){
        try{
            isMissing.missingID(id);
            Task task = repo.findById(id).orElseThrow(()-> new RuntimeException("Task não encontrada"));
             task.setTask(task.getTask());
                task.setStatus(task.getStatus());
                task.setCompleted(task.getCompleted());
                return repo.save(task);
        }catch(Exception e){
              return error.handleException(e);
        }
    }


    public void deletedTask(){}

}