package com.omgservers.module.system;

import com.omgservers.module.system.impl.service.eventService.EventService;
import com.omgservers.module.system.impl.service.handlerService.HandlerService;
import com.omgservers.module.system.impl.service.indexService.IndexService;
import com.omgservers.module.system.impl.service.jobService.JobService;
import com.omgservers.module.system.impl.service.logService.LogService;
import com.omgservers.module.system.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.module.system.impl.service.syncService.SyncService;

public interface SystemModule {

    ServiceAccountService getServiceAccountService();

    EventService getEventService();

    HandlerService getHandlerService();

    SyncService getSyncService();

    JobService getJobService();

    IndexService getIndexService();

    LogService getLogService();
}