package com.taskmanager.task.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanager.task.entity.Task;

public interface TaskRepository extends JpaRepository<Task, String> {
        Optional<Task> findById(String id);
}
