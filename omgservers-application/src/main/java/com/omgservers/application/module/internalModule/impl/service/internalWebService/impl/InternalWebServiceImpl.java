package com.omgservers.application.module.internalModule.impl.service.internalWebService.impl;

import com.omgservers.application.module.internalModule.impl.service.eventInternalService.EventInternalService;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.IndexHelpService;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.SyncIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.internalWebService.InternalWebService;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.JobInternalService;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.DeleteJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.SyncJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.DeleteJobInternalResponse;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.SyncJobInternalResponse;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.JobSchedulerService;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.ScheduleJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.UnscheduleJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.LogHelpService;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.response.ViewLogsHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.ServiceAccountHelpService;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class InternalWebServiceImpl implements InternalWebService {

    final ServiceAccountHelpService serviceAccountInternalService;
    final EventInternalService eventInternalService;
    final JobSchedulerService jobSchedulerService;
    final JobInternalService jobInternalService;
    final IndexHelpService indexHelpService;
    final LogHelpService logHelpService;

    @Override
    public Uni<Void> syncIndex(SyncIndexHelpRequest request) {
        return indexHelpService.syncIndex(request);
    }

    @Override
    public Uni<Void> syncServiceAccount(SyncServiceAccountHelpRequest request) {
        return serviceAccountInternalService.syncServiceAccount(request);
    }

    @Override
    public Uni<Void> fireEvent(FireEventInternalRequest request) {
        return eventInternalService.fireEvent(request);
    }

    @Override
    public Uni<SyncJobInternalResponse> syncJob(SyncJobInternalRequest request) {
        return jobInternalService.syncJob(request);
    }

    @Override
    public Uni<DeleteJobInternalResponse> deleteJob(DeleteJobInternalRequest request) {
        return jobInternalService.deleteJob(request);
    }

    @Override
    public Uni<Void> scheduleJob(ScheduleJobInternalRequest request) {
        return jobSchedulerService.scheduleJob(request);
    }

    @Override
    public Uni<Void> unscheduleJob(UnscheduleJobInternalRequest request) {
        return jobSchedulerService.unscheduleJob(request);
    }

    @Override
    public Uni<ViewLogsHelpResponse> viewLogs(final ViewLogsHelpRequest request) {
        return logHelpService.viewLogs(request);
    }
}
