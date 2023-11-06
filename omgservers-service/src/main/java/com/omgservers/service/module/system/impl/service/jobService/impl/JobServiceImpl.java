package com.omgservers.service.module.system.impl.service.jobService.impl;

import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.FindJobRequest;
import com.omgservers.model.dto.system.FindJobResponse;
import com.omgservers.model.dto.system.GetJobRequest;
import com.omgservers.model.dto.system.GetJobResponse;
import com.omgservers.model.dto.system.ScheduleJobRequest;
import com.omgservers.model.dto.system.SyncJobRequest;
import com.omgservers.model.dto.system.SyncJobResponse;
import com.omgservers.model.dto.system.UnscheduleJobRequest;
import com.omgservers.service.module.system.impl.service.jobService.JobService;
import com.omgservers.service.module.system.impl.service.jobService.impl.method.deleteJob.DeleteJobMethod;
import com.omgservers.service.module.system.impl.service.jobService.impl.method.findJob.FindJobMethod;
import com.omgservers.service.module.system.impl.service.jobService.impl.method.getJob.GetJobMethod;
import com.omgservers.service.module.system.impl.service.jobService.impl.method.scheduleJob.ScheduleJobMethod;
import com.omgservers.service.module.system.impl.service.jobService.impl.method.syncJob.SyncJobMethod;
import com.omgservers.service.module.system.impl.service.jobService.impl.method.unscheduleJob.UnscheduleJobMethod;
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

    final UnscheduleJobMethod unscheduleJobMethod;
    final ScheduleJobMethod scheduleJobMethod;
    final DeleteJobMethod deleteJobMethod;
    final SyncJobMethod syncJobMethod;
    final FindJobMethod findJobMethod;
    final GetJobMethod getJobMethod;

    @Override
    public Uni<GetJobResponse> getJob(@Valid final GetJobRequest request) {
        return getJobMethod.getJob(request);
    }

    @Override
    public Uni<FindJobResponse> findJob(@Valid final FindJobRequest request) {
        return findJobMethod.findJob(request);
    }

    @Override
    public Uni<SyncJobResponse> syncJob(@Valid final SyncJobRequest request) {
        return syncJobMethod.syncJob(request);
    }

    @Override
    public Uni<DeleteJobResponse> deleteJob(@Valid final DeleteJobRequest request) {
        return deleteJobMethod.deleteJob(request);
    }

    @Override
    public Uni<Void> scheduleJob(@Valid final ScheduleJobRequest request) {
        return scheduleJobMethod.scheduleJob(request);
    }

    @Override
    public Uni<Void> unscheduleJob(@Valid final UnscheduleJobRequest request) {
        return unscheduleJobMethod.unscheduleJob(request);
    }
}
