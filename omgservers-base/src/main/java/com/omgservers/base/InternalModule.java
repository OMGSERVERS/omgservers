package com.omgservers.base;

import com.omgservers.base.impl.service.eventHelpService.EventHelpService;
import com.omgservers.base.impl.service.eventInternalService.EventInternalService;
import com.omgservers.base.impl.service.indexHelpService.IndexHelpService;
import com.omgservers.base.impl.service.jobInternalService.JobInternalService;
import com.omgservers.base.impl.service.jobSchedulerService.JobSchedulerService;
import com.omgservers.base.impl.service.logHelpService.LogHelpService;
import com.omgservers.base.impl.service.serviceAccountHelpService.ServiceAccountHelpService;
import com.omgservers.base.impl.service.syncInternalService.SyncInternalService;

public interface InternalModule {

    EventHelpService getEventHelpService();

    EventInternalService getEventInternalService();

    IndexHelpService getIndexHelpService();

    JobInternalService getJobInternalService();

    JobSchedulerService getJobSchedulerService();

    ServiceAccountHelpService getServiceAccountHelpService();

    SyncInternalService getSyncInternalService();

    LogHelpService getLogHelpService();
}