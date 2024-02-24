package com.omgservers.service.module.system.impl;

import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.containerService.ContainerService;
import com.omgservers.service.module.system.impl.service.eventService.EventService;
import com.omgservers.service.module.system.impl.service.indexService.IndexService;
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
    final EventService eventService;
    final IndexService indexService;

    public EventService getEventService() {
        return eventService;
    }

    public IndexService getIndexService() {
        return indexService;
    }

    public ServiceAccountService getServiceAccountService() {
        return serviceAccountService;
    }

    @Override
    public ContainerService getContainerService() {
        return containerService;
    }
}
