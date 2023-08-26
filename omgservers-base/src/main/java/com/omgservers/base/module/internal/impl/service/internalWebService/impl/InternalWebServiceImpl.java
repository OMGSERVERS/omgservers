package com.omgservers.base.module.internal.impl.service.internalWebService.impl;

import com.omgservers.base.module.internal.impl.service.eventRoutedService.EventRoutedService;
import com.omgservers.base.module.internal.impl.service.indexService.IndexService;
import com.omgservers.base.module.internal.impl.service.internalWebService.InternalWebService;
import com.omgservers.base.module.internal.impl.service.jobRoutedService.JobRoutedService;
import com.omgservers.base.module.internal.impl.service.logService.LogService;
import com.omgservers.base.module.internal.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.dto.internalModule.DeleteJobRoutedRequest;
import com.omgservers.dto.internalModule.DeleteJobRoutedResponse;
import com.omgservers.dto.internalModule.FireEventRoutedRequest;
import com.omgservers.dto.internalModule.FireEventRoutedResponse;
import com.omgservers.dto.internalModule.ScheduleJobRoutedRequest;
import com.omgservers.dto.internalModule.SyncIndexRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
import com.omgservers.dto.internalModule.SyncServiceAccountRequest;
import com.omgservers.dto.internalModule.UnscheduleJobRoutedRequest;
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
    final EventRoutedService eventRoutedService;
    final JobRoutedService jobRoutedService;
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
    public Uni<FireEventRoutedResponse> fireEvent(FireEventRoutedRequest request) {
        return eventRoutedService.fireEvent(request);
    }

    @Override
    public Uni<SyncJobRoutedResponse> syncJob(SyncJobRoutedRequest request) {
        return jobRoutedService.syncJob(request);
    }

    @Override
    public Uni<DeleteJobRoutedResponse> deleteJob(DeleteJobRoutedRequest request) {
        return jobRoutedService.deleteJob(request);
    }

    @Override
    public Uni<Void> scheduleJob(ScheduleJobRoutedRequest request) {
        return jobRoutedService.scheduleJob(request);
    }

    @Override
    public Uni<Void> unscheduleJob(UnscheduleJobRoutedRequest request) {
        return jobRoutedService.unscheduleJob(request);
    }

    @Override
    public Uni<ViewLogsResponse> viewLogs(final ViewLogRequest request) {
        return logService.viewLogs(request);
    }
}
