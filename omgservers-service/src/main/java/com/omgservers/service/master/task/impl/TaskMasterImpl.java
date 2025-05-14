package com.omgservers.service.master.task.impl;

import com.omgservers.service.master.task.TaskMaster;
import com.omgservers.service.master.task.impl.service.taskService.TaskService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TaskMasterImpl implements TaskMaster {

    final TaskService taskService;

    @Override
    public TaskService getService() {
        return taskService;
    }
}
