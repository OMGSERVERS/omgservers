package com.omgservers.base.impl.service.internalWebService.impl;

import com.omgservers.base.impl.service.eventInternalService.EventInternalService;
import com.omgservers.base.impl.service.indexHelpService.IndexHelpService;
import com.omgservers.dto.internalModule.SyncIndexHelpRequest;
import com.omgservers.base.impl.service.internalWebService.InternalWebService;
import com.omgservers.base.impl.service.jobInternalService.JobInternalService;
import com.omgservers.base.impl.service.logHelpService.LogHelpService;
import com.omgservers.base.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.base.impl.service.logHelpService.response.ViewLogsHelpResponse;
import com.omgservers.base.impl.service.serviceAccountHelpService.ServiceAccountHelpService;
import com.omgservers.dto.internalModule.SyncServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.DeleteJobInternalRequest;
import com.omgservers.dto.internalModule.DeleteJobInternalResponse;
import com.omgservers.dto.internalModule.FireEventInternalRequest;
import com.omgservers.dto.internalModule.FireEventInternalResponse;
import com.omgservers.dto.internalModule.ScheduleJobInternalRequest;
import com.omgservers.dto.internalModule.SyncJobInternalRequest;
import com.omgservers.dto.internalModule.SyncJobInternalResponse;
import com.omgservers.dto.internalModule.UnscheduleJobInternalRequest;
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
//    final JobSchedulerService jobSchedulerService;
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
    public Uni<FireEventInternalResponse> fireEvent(FireEventInternalRequest request) {
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
//        return jobSchedulerService.scheduleJob(request);
        return null;
    }

    @Override
    public Uni<Void> unscheduleJob(UnscheduleJobInternalRequest request) {
//        return jobSchedulerService.unscheduleJob(request);
        return null;
    }

    @Override
    public Uni<ViewLogsHelpResponse> viewLogs(final ViewLogsHelpRequest request) {
        return logHelpService.viewLogs(request);
    }
}
