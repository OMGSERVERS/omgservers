package com.omgservers.module.internal.impl.service.jobShardedService.impl;

import com.omgservers.module.internal.impl.operation.getInternalModuleClient.GetInternalModuleClientOperation;
import com.omgservers.module.internal.impl.operation.getInternalModuleClient.InternalModuleClient;
import com.omgservers.module.internal.impl.service.jobShardedService.JobShardedService;
import com.omgservers.module.internal.impl.service.jobShardedService.impl.method.deleteJob.DeleteJobMethod;
import com.omgservers.module.internal.impl.service.jobShardedService.impl.method.scheduleJob.ScheduleJobMethod;
import com.omgservers.module.internal.impl.service.jobShardedService.impl.method.syncJob.SyncJobMethod;
import com.omgservers.module.internal.impl.service.jobShardedService.impl.method.unscheduleJob.UnscheduleJobMethod;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.internal.DeleteJobShardedRequest;
import com.omgservers.dto.internal.DeleteJobShardedResponse;
import com.omgservers.dto.internal.ScheduleJobShardedRequest;
import com.omgservers.dto.internal.SyncJobShardedRequest;
import com.omgservers.dto.internal.SyncJobShardedResponse;
import com.omgservers.dto.internal.UnscheduleJobShardedRequest;
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
    public Uni<SyncJobShardedResponse> syncJob(SyncJobShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncJobShardedRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::syncJob,
                syncJobMethod::syncJob);
    }

    @Override
    public Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteJobShardedRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::deleteJob,
                deleteJobMethod::deleteJob);
    }

    @Override
    public Uni<Void> scheduleJob(ScheduleJobShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                ScheduleJobShardedRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::scheduleJob,
                scheduleJobMethod::scheduleJob);
    }

    @Override
    public Uni<Void> unscheduleJob(UnscheduleJobShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                UnscheduleJobShardedRequest::validate,
                getInternalModuleClientOperation::getClient,
                InternalModuleClient::unscheduleJob,
                unscheduleJobMethod::unscheduleJob);
    }
}
