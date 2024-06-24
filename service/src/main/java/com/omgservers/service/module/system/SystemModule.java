package com.omgservers.service.module.system;

import com.omgservers.service.module.system.impl.service.bootstrapService.BootstrapService;
import com.omgservers.service.module.system.impl.service.eventService.EventService;
import com.omgservers.service.module.system.impl.service.indexService.IndexService;
import com.omgservers.service.module.system.impl.service.jobService.JobService;
import com.omgservers.service.module.system.impl.service.taskService.TaskService;

public interface SystemModule {

    BootstrapService getBootstrapService();

    EventService getEventService();

    IndexService getIndexService();

    TaskService getTaskService();

    JobService getJobService();
}