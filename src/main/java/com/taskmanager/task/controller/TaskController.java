package com.taskmanager.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.taskmanager.task.entity.Task;
import com.taskmanager.task.service.TaskHooks;
import com.taskmanager.task.service.TaskService;
import com.taskmanager.task.dtos.TaskDto;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("api/task")
@ResponseBody
public class TaskController {

    private TaskService taskService;
    private TaskHooks taskHook;

    public TaskController(TaskService taskService, TaskHooks taskHook) {
        this.taskHook = taskHook;
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public Page<Task> getAllTask(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            return taskService.getAllTaks(page, limit);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id) {
        try {
            return taskService.getTaskById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody TaskDto body) {
        try {
            return taskService.createTask(body);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody TaskDto body) {
        try {
            return taskService.update(id, body);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PatchMapping("/{id}")
    public Task patchTask(@PathVariable String id, @RequestBody TaskDto body) {
        try {
            return taskService.patchTasks(id, body);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String id) {
        try {
            taskService.deletedTask();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/completed")
    public List<Task> getCompletedTasks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            return taskHook.tasksCompleted(page, limit);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/pending")
    public List<Task> getPendingTasks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            return taskHook.taskPendente(page, limit);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
