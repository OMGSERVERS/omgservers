package com.omgservers.module.system;

import com.omgservers.module.system.impl.service.containerService.ContainerService;
import com.omgservers.module.system.impl.service.eventService.EventService;
import com.omgservers.module.system.impl.service.handlerService.HandlerService;
import com.omgservers.module.system.impl.service.indexService.IndexService;
import com.omgservers.module.system.impl.service.jobService.JobService;
import com.omgservers.module.system.impl.service.logService.LogService;
import com.omgservers.module.system.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.module.system.impl.service.syncService.SyncService;

public interface SystemModule {

    ServiceAccountService getServiceAccountService();

    ContainerService getContainerService();

    HandlerService getHandlerService();

    EventService getEventService();

    IndexService getIndexService();
    
    SyncService getSyncService();

    JobService getJobService();

    LogService getLogService();


}