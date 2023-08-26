package com.omgservers.base.module.internal.impl.service.jobRoutedService.impl;

import com.omgservers.base.module.internal.impl.operation.getInternalModuleClient.GetInternalModuleClientOperation;
import com.omgservers.base.module.internal.impl.operation.getInternalModuleClient.InternalModuleClient;
import com.omgservers.base.module.internal.impl.service.jobRoutedService.JobRoutedService;
import com.omgservers.base.module.internal.impl.service.jobRoutedService.impl.method.deleteJob.DeleteJobMethod;
import com.omgservers.base.module.internal.impl.service.jobRoutedService.impl.method.scheduleJob.ScheduleJobMethod;
import com.omgservers.base.module.internal.impl.service.jobRoutedService.impl.method.syncJob.SyncJobMethod;
import com.omgservers.base.module.internal.impl.service.jobRoutedService.impl.method.unscheduleJob.UnscheduleJobMethod;
import com.omgservers.base.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.internalModule.DeleteJobRoutedRequest;
import com.omgservers.dto.internalModule.DeleteJobRoutedResponse;
import com.omgservers.dto.internalModule.ScheduleJobRoutedRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
import com.omgservers.dto.internalModule.UnscheduleJobRoutedRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class JobRoutedServiceImpl implements JobRoutedService {

    final UnscheduleJobMethod unscheduleJobMethod;
    final ScheduleJobMethod scheduleJobMethod;
    final DeleteJobMethod deleteJobMethod;
    final SyncJobMethod syncJobMethod;

    final GetInternalModuleClientOperation getInternalModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<SyncJobRoutedResponse> syncJob(SyncJobRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncJobRoutedRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::syncJob,
                syncJobMethod::syncJob);
    }

    @Override
    public Uni<DeleteJobRoutedResponse> deleteJob(DeleteJobRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteJobRoutedRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::deleteJob,
                deleteJobMethod::deleteJob);
    }

    @Override
    public Uni<Void> scheduleJob(ScheduleJobRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                ScheduleJobRoutedRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::scheduleJob,
                scheduleJobMethod::scheduleJob);
    }

    @Override
    public Uni<Void> unscheduleJob(UnscheduleJobRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                UnscheduleJobRoutedRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::unscheduleJob,
                unscheduleJobMethod::unscheduleJob);
    }
}
