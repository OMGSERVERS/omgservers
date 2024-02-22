package com.omgservers.service.module.system.impl;

import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.containerService.ContainerService;
import com.omgservers.service.module.system.impl.service.entityService.EntityService;
import com.omgservers.service.module.system.impl.service.eventService.EventService;
import com.omgservers.service.module.system.impl.service.handlerService.HandlerService;
import com.omgservers.service.module.system.impl.service.indexService.IndexService;
import com.omgservers.service.module.system.impl.service.jobService.JobService;
import com.omgservers.service.module.system.impl.service.logService.LogService;
import com.omgservers.service.module.system.impl.service.serviceAccountService.ServiceAccountService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SystemModuleImpl implements SystemModule {

    final ServiceAccountService serviceAccountService;
    final ContainerService containerService;
    final HandlerService handlerService;
    final EntityService entityService;
    final EventService eventService;
    final IndexService indexService;
    final JobService jobService;
    final LogService logService;

    public EventService getEventService() {
        return eventService;
    }

    @Override
    public HandlerService getHandlerService() {
        return handlerService;
    }

    public IndexService getIndexService() {
        return indexService;
    }

    public JobService getJobService() {
        return jobService;
    }

    public ServiceAccountService getServiceAccountService() {
        return serviceAccountService;
    }

    public LogService getLogService() {
        return logService;
    }

    @Override
    public ContainerService getContainerService() {
        return containerService;
    }

    @Override
    public EntityService getEntityService() {
        return entityService;
    }
}
