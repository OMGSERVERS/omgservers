package com.omgservers.module.system.impl.service.jobService.impl;

import com.omgservers.module.system.impl.operation.getInternalModuleClient.GetInternalModuleClientOperation;
import com.omgservers.module.system.impl.operation.getInternalModuleClient.SystemModuleClient;
import com.omgservers.module.system.impl.service.jobService.JobService;
import com.omgservers.module.system.impl.service.jobService.impl.method.deleteJob.DeleteJobMethod;
import com.omgservers.module.system.impl.service.jobService.impl.method.scheduleJob.ScheduleJobMethod;
import com.omgservers.module.system.impl.service.jobService.impl.method.syncJob.SyncJobMethod;
import com.omgservers.module.system.impl.service.jobService.impl.method.unscheduleJob.UnscheduleJobMethod;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.internal.DeleteJobRequest;
import com.omgservers.dto.internal.DeleteJobResponse;
import com.omgservers.dto.internal.ScheduleJobRequest;
import com.omgservers.dto.internal.SyncJobRequest;
import com.omgservers.dto.internal.SyncJobResponse;
import com.omgservers.dto.internal.UnscheduleJobRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class JobServiceImpl implements JobService {

    final UnscheduleJobMethod unscheduleJobMethod;
    final ScheduleJobMethod scheduleJobMethod;
    final DeleteJobMethod deleteJobMethod;
    final SyncJobMethod syncJobMethod;

    final GetInternalModuleClientOperation getInternalModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<SyncJobResponse> syncJob(SyncJobRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncJobRequest::validate,
                getInternalModuleClientOperation::getClient,
                SystemModuleClient::syncJob,
                syncJobMethod::syncJob);
    }

    @Override
    public Uni<DeleteJobResponse> deleteJob(DeleteJobRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteJobRequest::validate,
                getInternalModuleClientOperation::getClient,
                SystemModuleClient::deleteJob,
                deleteJobMethod::deleteJob);
    }

    @Override
    public Uni<Void> scheduleJob(ScheduleJobRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                ScheduleJobRequest::validate,
                getInternalModuleClientOperation::getClient,
                SystemModuleClient::scheduleJob,
                scheduleJobMethod::scheduleJob);
    }

    @Override
    public Uni<Void> unscheduleJob(UnscheduleJobRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                UnscheduleJobRequest::validate,
                getInternalModuleClientOperation::getClient,
                SystemModuleClient::unscheduleJob,
                unscheduleJobMethod::unscheduleJob);
    }
}
