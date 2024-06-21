package com.omgservers.service.module.system.impl;

import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.bootstrapService.BootstrapService;
import com.omgservers.service.module.system.impl.service.eventService.EventService;
import com.omgservers.service.module.system.impl.service.indexService.IndexService;
import com.omgservers.service.module.system.impl.service.jobService.JobService;
import com.omgservers.service.module.system.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.service.module.system.impl.service.taskService.TaskService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SystemModuleImpl implements SystemModule {

    final ServiceAccountService serviceAccountService;
    final BootstrapService bootstrapService;
    final EventService eventService;
    final IndexService indexService;
    final TaskService taskService;
    final JobService jobService;

    @Override
    public ServiceAccountService getServiceAccountService() {
        return serviceAccountService;
    }

    @Override
    public BootstrapService getBootstrapService() {
        return bootstrapService;
    }

    @Override
    public EventService getEventService() {
        return eventService;
    }

    @Override
    public IndexService getIndexService() {
        return indexService;
    }

    @Override
    public TaskService getTaskService() {
        return taskService;
    }

    @Override
    public JobService getJobService() {
        return jobService;
    }
}
