package com.taskmanager.task.dtos;


import com.taskmanager.task.utils.status.StatusTask;
import com.taskmanager.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class TaskDto {
  private String id;
  private String task;
  private Boolean completed;
  private StatusTask status;
  private String userId;

}
