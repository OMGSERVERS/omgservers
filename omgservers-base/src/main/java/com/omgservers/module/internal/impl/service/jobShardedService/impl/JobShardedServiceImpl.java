package com.omgservers.module.internal.impl.service.jobShardedService.impl;

import com.omgservers.module.internal.impl.operation.getInternalModuleClient.GetInternalModuleClientOperation;
import com.omgservers.module.internal.impl.operation.getInternalModuleClient.InternalModuleClient;
import com.omgservers.module.internal.impl.service.jobShardedService.JobShardedService;
import com.omgservers.module.internal.impl.service.jobShardedService.impl.method.deleteJob.DeleteJobMethod;
import com.omgservers.module.internal.impl.service.jobShardedService.impl.method.scheduleJob.ScheduleJobMethod;
import com.omgservers.module.internal.impl.service.jobShardedService.impl.method.syncJob.SyncJobMethod;
import com.omgservers.module.internal.impl.service.jobShardedService.impl.method.unscheduleJob.UnscheduleJobMethod;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.internalModule.DeleteJobShardRequest;
import com.omgservers.dto.internalModule.DeleteJobShardedResponse;
import com.omgservers.dto.internalModule.ScheduleJobShardRequest;
import com.omgservers.dto.internalModule.SyncJobShardRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
import com.omgservers.dto.internalModule.UnscheduleJobShardRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class JobShardedServiceImpl implements JobShardedService {

    final UnscheduleJobMethod unscheduleJobMethod;
    final ScheduleJobMethod scheduleJobMethod;
    final DeleteJobMethod deleteJobMethod;
    final SyncJobMethod syncJobMethod;

    final GetInternalModuleClientOperation getInternalModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<SyncJobRoutedResponse> syncJob(SyncJobShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncJobShardRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::syncJob,
                syncJobMethod::syncJob);
    }

    @Override
    public Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteJobShardRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::deleteJob,
                deleteJobMethod::deleteJob);
    }

    @Override
    public Uni<Void> scheduleJob(ScheduleJobShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                ScheduleJobShardRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::scheduleJob,
                scheduleJobMethod::scheduleJob);
    }

    @Override
    public Uni<Void> unscheduleJob(UnscheduleJobShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                UnscheduleJobShardRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::unscheduleJob,
                unscheduleJobMethod::unscheduleJob);
    }
}
