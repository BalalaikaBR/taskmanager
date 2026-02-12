package com.taskmanager.task.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import com.taskmanager.task.utils.status.StatusTask;
import com.taskmanager.utils.BaseEntity;
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Task extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String task;
    @Column(nullable = false)
    private Boolean completed = false;

    @Column(nullable = false)
    private StatusTask status = StatusTask.ANDAMENTO;

}
