package com.omgservers.service.module.system.impl.service.jobService.impl;

import com.omgservers.schema.service.system.job.DeleteJobRequest;
import com.omgservers.schema.service.system.job.DeleteJobResponse;
import com.omgservers.schema.service.system.job.FindJobRequest;
import com.omgservers.schema.service.system.job.FindJobResponse;
import com.omgservers.schema.service.system.job.GetJobRequest;
import com.omgservers.schema.service.system.job.GetJobResponse;
import com.omgservers.schema.service.system.job.SyncJobRequest;
import com.omgservers.schema.service.system.job.SyncJobResponse;
import com.omgservers.schema.service.system.job.ViewJobsRequest;
import com.omgservers.schema.service.system.job.ViewJobsResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.system.impl.service.jobService.JobService;
import com.omgservers.service.module.system.impl.service.jobService.impl.method.deleteJob.DeleteJobMethod;
import com.omgservers.service.module.system.impl.service.jobService.impl.method.findJob.FindJobMethod;
import com.omgservers.service.module.system.impl.service.jobService.impl.method.getJob.GetJobMethod;
import com.omgservers.service.module.system.impl.service.jobService.impl.method.syncJob.SyncJobMethod;
import com.omgservers.service.module.system.impl.service.jobService.impl.method.viewJobs.ViewJobsMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class JobServiceImpl implements JobService {

    final DeleteJobMethod deleteJobMethod;
    final ViewJobsMethod viewJobsMethod;
    final SyncJobMethod syncJobMethod;
    final FindJobMethod findJobMethod;
    final GetJobMethod getJobMethod;

    @Override
    public Uni<GetJobResponse> getJob(GetJobRequest request) {
        return getJobMethod.getJob(request);
    }

    @Override
    public Uni<FindJobResponse> findJob(FindJobRequest request) {
        return findJobMethod.findJob(request);
    }

    @Override
    public Uni<ViewJobsResponse> viewJobs(ViewJobsRequest request) {
        return viewJobsMethod.viewJobs(request);
    }

    @Override
    public Uni<SyncJobResponse> syncJob(SyncJobRequest request) {
        return syncJobMethod.syncJob(request);
    }

    @Override
    public Uni<SyncJobResponse> syncJobWithIdempotency(@Valid SyncJobRequest request) {
        return syncJob(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getJob(), t.getMessage());
                            return Uni.createFrom().item(new SyncJobResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteJobResponse> deleteJob(@Valid final DeleteJobRequest request) {
        return deleteJobMethod.deleteJob(request);
    }
}
