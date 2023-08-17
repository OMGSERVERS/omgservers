package com.omgservers.application.module.internalModule.impl;

import com.omgservers.application.module.internalModule.InternalModule;
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
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class InternalModuleImpl implements InternalModule {

    final ServiceAccountHelpService serviceAccountHelpService;
    final EventInternalService eventInternalService;
    final ConsumerHelpService consumerHelpService;
    final ProducerHelpService producerHelpService;
    final JobSchedulerService jobSchedulerService;
    final SyncInternalService syncInternalService;
    final JobInternalService jobInternalService;
    final IndexHelpService indexHelpService;
    final EventHelpService eventHelpService;
    final LogHelpService logHelpService;

    @Override
    public ConsumerHelpService getConsumerHelpService() {
        return consumerHelpService;
    }

    @Override
    public ProducerHelpService getProducerHelpService() {
        return producerHelpService;
    }

    @Override
    public EventHelpService getEventHelpService() {
        return eventHelpService;
    }

    @Override
    public EventInternalService getEventInternalService() {
        return eventInternalService;
    }

    public IndexHelpService getIndexHelpService() {
        return indexHelpService;
    }

    @Override
    public JobInternalService getJobInternalService() {
        return jobInternalService;
    }

    @Override
    public JobSchedulerService getJobSchedulerService() {
        return jobSchedulerService;
    }

    public ServiceAccountHelpService getServiceAccountHelpService() {
        return serviceAccountHelpService;
    }

    @Override
    public SyncInternalService getSyncInternalService() {
        return syncInternalService;
    }

    @Override
    public LogHelpService getLogHelpService() {
        return logHelpService;
    }
}
