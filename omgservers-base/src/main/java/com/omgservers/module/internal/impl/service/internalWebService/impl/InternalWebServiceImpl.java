package com.omgservers.module.internal.impl.service.internalWebService.impl;

import com.omgservers.module.internal.impl.service.eventShardedService.EventShardedService;
import com.omgservers.module.internal.impl.service.indexService.IndexService;
import com.omgservers.module.internal.impl.service.internalWebService.InternalWebService;
import com.omgservers.module.internal.impl.service.jobShardedService.JobShardedService;
import com.omgservers.module.internal.impl.service.logService.LogService;
import com.omgservers.module.internal.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.dto.internalModule.DeleteJobShardRequest;
import com.omgservers.dto.internalModule.DeleteJobShardedResponse;
import com.omgservers.dto.internalModule.FireEventShardRequest;
import com.omgservers.dto.internalModule.FireEventShardedResponse;
import com.omgservers.dto.internalModule.ScheduleJobShardRequest;
import com.omgservers.dto.internalModule.SyncIndexRequest;
import com.omgservers.dto.internalModule.SyncJobShardRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
import com.omgservers.dto.internalModule.SyncServiceAccountRequest;
import com.omgservers.dto.internalModule.UnscheduleJobShardRequest;
import com.omgservers.dto.internalModule.ViewLogRequest;
import com.omgservers.dto.internalModule.ViewLogsResponse;
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
    public Uni<FireEventShardedResponse> fireEvent(FireEventShardRequest request) {
        return eventShardedService.fireEvent(request);
    }

    @Override
    public Uni<SyncJobRoutedResponse> syncJob(SyncJobShardRequest request) {
        return jobShardedService.syncJob(request);
    }

    @Override
    public Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardRequest request) {
        return jobShardedService.deleteJob(request);
    }

    @Override
    public Uni<Void> scheduleJob(ScheduleJobShardRequest request) {
        return jobShardedService.scheduleJob(request);
    }

    @Override
    public Uni<Void> unscheduleJob(UnscheduleJobShardRequest request) {
        return jobShardedService.unscheduleJob(request);
    }

    @Override
    public Uni<ViewLogsResponse> viewLogs(final ViewLogRequest request) {
        return logService.viewLogs(request);
    }
}
