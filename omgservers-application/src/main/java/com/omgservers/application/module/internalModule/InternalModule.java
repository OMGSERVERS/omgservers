package com.omgservers.application.module.internalModule;

import com.omgservers.application.module.internalModule.impl.service.consumerHelpService.ConsumerHelpService;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.EventHelpService;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.EventInternalService;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.IndexHelpService;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.JobInternalService;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.JobSchedulerService;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.LogHelpService;
import com.omgservers.application.module.internalModule.impl.service.producerHelpService.ProducerHelpService;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.ServiceAccountHelpService;
import com.omgservers.application.module.internalModule.impl.service.syncInternalService.SyncInternalService;

public interface InternalModule {

    ConsumerHelpService getConsumerHelpService();

    ProducerHelpService getProducerHelpService();

    EventHelpService getEventHelpService();

    EventInternalService getEventInternalService();

    IndexHelpService getIndexHelpService();

    JobInternalService getJobInternalService();

    JobSchedulerService getJobSchedulerService();

    ServiceAccountHelpService getServiceAccountHelpService();

    SyncInternalService getSyncInternalService();

    LogHelpService getLogHelpService();
}