package com.omgservers.module.internal;

import com.omgservers.module.internal.impl.service.changeService.ChangeService;
import com.omgservers.module.internal.impl.service.eventShardedService.EventShardedService;
import com.omgservers.module.internal.impl.service.indexService.IndexService;
import com.omgservers.module.internal.impl.service.jobShardedService.JobShardedService;
import com.omgservers.module.internal.impl.service.logService.LogService;
import com.omgservers.module.internal.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.module.internal.impl.service.syncService.SyncService;

public interface InternalModule {

    ServiceAccountService getServiceAccountService();

    EventShardedService getEventShardedService();

    SyncService getSyncService();

    JobShardedService getJobShardedService();

    ChangeService getChangeService();

    IndexService getIndexService();

    LogService getLogService();
}