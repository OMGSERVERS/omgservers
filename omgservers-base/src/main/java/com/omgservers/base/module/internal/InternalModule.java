package com.omgservers.base.module.internal;

import com.omgservers.base.module.internal.impl.service.changeService.ChangeService;
import com.omgservers.base.module.internal.impl.service.eventRoutedService.EventRoutedService;
import com.omgservers.base.module.internal.impl.service.eventService.EventService;
import com.omgservers.base.module.internal.impl.service.indexService.IndexService;
import com.omgservers.base.module.internal.impl.service.jobRoutedService.JobRoutedService;
import com.omgservers.base.module.internal.impl.service.logService.LogService;
import com.omgservers.base.module.internal.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.base.module.internal.impl.service.syncService.SyncService;

public interface InternalModule {

    ServiceAccountService getServiceAccountService();

    EventRoutedService getEventRoutedService();

    SyncService getSyncService();

    JobRoutedService getJobRoutedService();

    ChangeService getChangeService();

    EventService getEventService();

    IndexService getIndexService();

    LogService getLogService();
}