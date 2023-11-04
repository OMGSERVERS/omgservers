package com.omgservers.service.module.system.impl.service.webService.impl;

import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.ScheduleJobRequest;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncJobRequest;
import com.omgservers.model.dto.system.SyncJobResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.UnscheduleJobRequest;
import com.omgservers.model.dto.system.ViewLogRequest;
import com.omgservers.model.dto.system.ViewLogsResponse;
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
    final EventService eventService;
    final IndexService indexService;
    final JobService jobService;
    final LogService logService;

    @Override
    public Uni<Void> syncIndex(SyncIndexRequest request) {
        return indexService.syncIndex(request);
    }

    @Override
    public Uni<Void> syncServiceAccount(SyncServiceAccountRequest request) {
        return serviceAccountService.syncServiceAccount(request);
    }

    @Override
    public Uni<SyncJobResponse> syncJob(SyncJobRequest request) {
        return jobService.syncJob(request);
    }

    @Override
    public Uni<DeleteJobResponse> deleteJob(DeleteJobRequest request) {
        return jobService.deleteJob(request);
    }

    @Override
    public Uni<Void> scheduleJob(ScheduleJobRequest request) {
        return jobService.scheduleJob(request);
    }

    @Override
    public Uni<Void> unscheduleJob(UnscheduleJobRequest request) {
        return jobService.unscheduleJob(request);
    }

    @Override
    public Uni<ViewLogsResponse> viewLogs(final ViewLogRequest request) {
        return logService.viewLogs(request);
    }
}
