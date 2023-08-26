package com.omgservers.base.module.internal.impl;

import com.omgservers.base.module.internal.InternalModule;
import com.omgservers.base.module.internal.impl.service.changeService.ChangeService;
import com.omgservers.base.module.internal.impl.service.eventRoutedService.EventRoutedService;
import com.omgservers.base.module.internal.impl.service.eventService.EventService;
import com.omgservers.base.module.internal.impl.service.indexService.IndexService;
import com.omgservers.base.module.internal.impl.service.jobRoutedService.JobRoutedService;
import com.omgservers.base.module.internal.impl.service.logService.LogService;
import com.omgservers.base.module.internal.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.base.module.internal.impl.service.syncService.SyncService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class InternalModuleImpl implements InternalModule {

    final ServiceAccountService serviceAccountService;
    final EventRoutedService eventRoutedService;
    final JobRoutedService jobRoutedService;
    final ChangeService changeService;
    final IndexService indexService;
    final EventService eventService;
    final SyncService syncService;
    final LogService logService;

    public EventService getEventService() {
        return eventService;
    }

    @Override
    public EventRoutedService getEventRoutedService() {
        return eventRoutedService;
    }

    public IndexService getIndexService() {
        return indexService;
    }

    public JobRoutedService getJobRoutedService() {
        return jobRoutedService;
    }

    public ChangeService getChangeService() {
        return changeService;
    }

    public ServiceAccountService getServiceAccountService() {
        return serviceAccountService;
    }

    public SyncService getSyncService() {
        return syncService;
    }

    public LogService getLogService() {
        return logService;
    }
}
