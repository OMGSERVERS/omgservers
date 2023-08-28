package com.omgservers.module.internal.impl.service.internalWebService.impl;

import com.omgservers.module.internal.impl.service.eventShardedService.EventShardedService;
import com.omgservers.module.internal.impl.service.indexService.IndexService;
import com.omgservers.module.internal.impl.service.internalWebService.InternalWebService;
import com.omgservers.module.internal.impl.service.jobShardedService.JobShardedService;
import com.omgservers.module.internal.impl.service.logService.LogService;
import com.omgservers.module.internal.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.dto.internal.DeleteJobShardedRequest;
import com.omgservers.dto.internal.DeleteJobShardedResponse;
import com.omgservers.dto.internal.FireEventShardedRequest;
import com.omgservers.dto.internal.FireEventShardedResponse;
import com.omgservers.dto.internal.ScheduleJobShardedRequest;
import com.omgservers.dto.internal.SyncIndexRequest;
import com.omgservers.dto.internal.SyncJobShardedRequest;
import com.omgservers.dto.internal.SyncJobShardedResponse;
import com.omgservers.dto.internal.SyncServiceAccountRequest;
import com.omgservers.dto.internal.UnscheduleJobShardedRequest;
import com.omgservers.dto.internal.ViewLogRequest;
import com.omgservers.dto.internal.ViewLogsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class InternalWebServiceImpl implements InternalWebService {

    final ServiceAccountService serviceAccountInternalService;
    final EventShardedService eventShardedService;
    final JobShardedService jobShardedService;
    final IndexService indexService;
    final LogService logService;

    @Override
    public Uni<Void> syncIndex(SyncIndexRequest request) {
        return indexService.syncIndex(request);
    }

    @Override
    public Uni<Void> syncServiceAccount(SyncServiceAccountRequest request) {
        return serviceAccountInternalService.syncServiceAccount(request);
    }

    @Override
    public Uni<FireEventShardedResponse> fireEvent(FireEventShardedRequest request) {
        return eventShardedService.fireEvent(request);
    }

    @Override
    public Uni<SyncJobShardedResponse> syncJob(SyncJobShardedRequest request) {
        return jobShardedService.syncJob(request);
    }

    @Override
    public Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardedRequest request) {
        return jobShardedService.deleteJob(request);
    }

    @Override
    public Uni<Void> scheduleJob(ScheduleJobShardedRequest request) {
        return jobShardedService.scheduleJob(request);
    }

    @Override
    public Uni<Void> unscheduleJob(UnscheduleJobShardedRequest request) {
        return jobShardedService.unscheduleJob(request);
    }

    @Override
    public Uni<ViewLogsResponse> viewLogs(final ViewLogRequest request) {
        return logService.viewLogs(request);
    }
}
