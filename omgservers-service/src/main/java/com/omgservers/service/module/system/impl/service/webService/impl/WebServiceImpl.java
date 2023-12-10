package com.omgservers.service.module.system.impl.service.webService.impl;

import com.omgservers.model.dto.system.DeleteEntityRequest;
import com.omgservers.model.dto.system.DeleteEntityResponse;
import com.omgservers.model.dto.system.DeleteIndexRequest;
import com.omgservers.model.dto.system.DeleteIndexResponse;
import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.DeleteServiceAccountRequest;
import com.omgservers.model.dto.system.DeleteServiceAccountResponse;
import com.omgservers.model.dto.system.FindEntityRequest;
import com.omgservers.model.dto.system.FindEntityResponse;
import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.dto.system.FindJobRequest;
import com.omgservers.model.dto.system.FindJobResponse;
import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import com.omgservers.model.dto.system.GetIndexRequest;
import com.omgservers.model.dto.system.GetIndexResponse;
import com.omgservers.model.dto.system.GetJobRequest;
import com.omgservers.model.dto.system.GetJobResponse;
import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import com.omgservers.model.dto.system.ScheduleJobRequest;
import com.omgservers.model.dto.system.SyncEntityRequest;
import com.omgservers.model.dto.system.SyncEntityResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.dto.system.SyncJobRequest;
import com.omgservers.model.dto.system.SyncJobResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
import com.omgservers.model.dto.system.UnscheduleJobRequest;
import com.omgservers.model.dto.system.ViewLogRequest;
import com.omgservers.model.dto.system.ViewLogsResponse;
import com.omgservers.service.module.system.impl.service.entityService.EntityService;
import com.omgservers.service.module.system.impl.service.eventService.EventService;
import com.omgservers.service.module.system.impl.service.indexService.IndexService;
import com.omgservers.service.module.system.impl.service.jobService.JobService;
import com.omgservers.service.module.system.impl.service.logService.LogService;
import com.omgservers.service.module.system.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.service.module.system.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final ServiceAccountService serviceAccountService;
    final EntityService entityService;
    final EventService eventService;
    final IndexService indexService;
    final JobService jobService;
    final LogService logService;

    @Override
    public Uni<GetIndexResponse> getIndex(final GetIndexRequest request) {
        return indexService.getIndex(request);
    }

    @Override
    public Uni<FindIndexResponse> findIndex(final FindIndexRequest request) {
        return indexService.findIndex(request);
    }

    @Override
    public Uni<SyncIndexResponse> syncIndex(final SyncIndexRequest request) {
        return indexService.syncIndex(request);
    }

    @Override
    public Uni<DeleteIndexResponse> deleteIndex(final DeleteIndexRequest request) {
        return indexService.deleteIndex(request);
    }

    @Override
    public Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request) {
        return serviceAccountService.getServiceAccount(request);
    }

    @Override
    public Uni<FindServiceAccountResponse> findServiceAccount(FindServiceAccountRequest request) {
        return serviceAccountService.findServiceAccount(request);
    }

    @Override
    public Uni<SyncServiceAccountResponse> syncServiceAccount(final SyncServiceAccountRequest request) {
        return serviceAccountService.syncServiceAccount(request);
    }

    @Override
    public Uni<DeleteServiceAccountResponse> deleteServiceAccount(final DeleteServiceAccountRequest request) {
        return serviceAccountService.deleteServiceAccount(request);
    }

    @Override
    public Uni<GetJobResponse> getJob(final GetJobRequest request) {
        return jobService.getJob(request);
    }

    @Override
    public Uni<FindJobResponse> findJob(final FindJobRequest request) {
        return jobService.findJob(request);
    }

    @Override
    public Uni<SyncJobResponse> syncJob(final SyncJobRequest request) {
        return jobService.syncJob(request);
    }

    @Override
    public Uni<DeleteJobResponse> deleteJob(final DeleteJobRequest request) {
        return jobService.deleteJob(request);
    }

    @Override
    public Uni<Void> scheduleJob(final ScheduleJobRequest request) {
        return jobService.scheduleJob(request);
    }

    @Override
    public Uni<Void> unscheduleJob(final UnscheduleJobRequest request) {
        return jobService.unscheduleJob(request);
    }

    @Override
    public Uni<ViewLogsResponse> viewLogs(final ViewLogRequest request) {
        return logService.viewLogs(request);
    }

    @Override
    public Uni<FindEntityResponse> findEntity(final FindEntityRequest request) {
        return entityService.findEntity(request);
    }

    @Override
    public Uni<SyncEntityResponse> syncEntity(final SyncEntityRequest request) {
        return entityService.syncEntity(request);
    }

    @Override
    public Uni<DeleteEntityResponse> deleteEntity(final DeleteEntityRequest request) {
        return entityService.deleteEntity(request);
    }
}
